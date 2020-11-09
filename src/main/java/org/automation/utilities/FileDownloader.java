package org.automation.utilities;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.http.client.protocol.HttpClientContext.COOKIE_STORE;
import static org.apache.http.impl.client.HttpClientBuilder.create;
import static org.apache.pdfbox.pdmodel.PDDocument.load;
import static org.automation.logger.Log.info;
import static org.automation.utilities.RequestType.GET;
import static org.automation.utilities.RequestType.PATCH;
import static org.automation.utilities.RequestType.POST;
import static org.automation.utilities.RequestType.PUT;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 * To handle file downloading.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 08/31/2020
 *
 */
public final class FileDownloader {

	private WebDriver driver;
	private RequestType httpRequestMethod = GET;
	private URI fileURI;
	private List<NameValuePair> urlParameters;

	/**
	 * Create an instance of file downloader.
	 * 
	 * @param driver current instance of web driver.
	 */
	public FileDownloader(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Set the HTTP Request Method.
	 * 
	 * @param requestType type of request
	 */
	public void setHttpRequestMethod(RequestType requestType) {
		httpRequestMethod = requestType;
	}

	/**
	 * Set the URL Parameters.
	 * 
	 * @param urlParameters parameters of the URL
	 */
	public void setUrlParameters(List<NameValuePair> urlParameters) {
		this.urlParameters = urlParameters;
	}

	/**
	 * Set the URI.
	 * 
	 * @param linkToFile URI
	 */
	public void setURI(URI linkToFile) {
		fileURI = linkToFile;
	}

	/**
	 * Get the HTTP Status of the specific URI.
	 * 
	 * @return HTTP Status
	 * @throws IOException
	 */
	public int getLinkHttpStatus() throws IOException {
		HttpResponse downloadableFile = makeHttpConnection();
		int httpStatusCode;
		try {
			httpStatusCode = downloadableFile.getStatusLine().getStatusCode();
		} finally {
			downloadableFile.getEntity().getContent().close();
		}
		return httpStatusCode;
	}

	/**
	 * Download the file from the specified URI.
	 * 
	 * @param extension file extension
	 * @return downloaded file
	 * @throws IOException
	 */
	public File downloadFile(String extension) throws IOException {
		Path downloadedFile = Files.createTempFile("download", extension);
		HttpResponse downloadableFile = makeHttpConnection();
		try {
			InputStream in = downloadableFile.getEntity().getContent();
			Files.copy(in, downloadedFile, REPLACE_EXISTING);
		} finally {
			downloadableFile.getEntity().getContent().close();
		}
		info("Downloaded file: " + downloadedFile);
		return downloadedFile.toFile();
	}

	/**
	 * Get the data from the file present in the specified URI.
	 * 
	 * @param extension file extension
	 * @return file data
	 * @throws IOException
	 */
	public String getLinkHttpData(String extension) throws IOException {
		HttpResponse downloadableFile = makeHttpConnection();
		String data = "";
		try {
			switch (extension) {
			case ".txt":
				data = EntityUtils.toString(downloadableFile.getEntity());
			case ".pdf":
				if (downloadableFile.getEntity().getContentType().getValue().equals("application/pdf")) {
					PDDocument document = load(downloadableFile.getEntity().getContent());
					if (document.isEncrypted()) {
						data = "PDF Document is encrypted";
					} else {
						data = new PDFTextStripper().getText(document);
					}
					document.close();
				}
			default:
				data = "Unable to get Data for " + extension + " extension!";
			}
		} finally {
			downloadableFile.getEntity().getContent().close();
		}
		return data;
	}

	/**
	 * Make the HTTP Connection to the specified URI.
	 * 
	 * @return HTTP Response
	 * @throws IOException
	 */
	private HttpResponse makeHttpConnection() throws IOException {
		if (fileURI == null) {
			throw new NullPointerException("No file URI specified");
		}
		HttpClient client = create().build();
		HttpRequestBase requestMethod = httpRequestMethod.getRequestMethod();
		requestMethod.setURI(fileURI);
		BasicHttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(COOKIE_STORE, getWebDriverCookies(driver.manage().getCookies()));
		requestMethod.setHeader("User-Agent", getWebDriverUserAgent());
		if (urlParameters != null && (httpRequestMethod.equals(PATCH) || httpRequestMethod.equals(POST)
				|| httpRequestMethod.equals(PUT))) {
			((HttpEntityEnclosingRequestBase) requestMethod).setEntity(new UrlEncodedFormEntity(urlParameters));
		}
		return client.execute(requestMethod, localContext);
	}

	/**
	 * Get all the cookies present in the current browser instance.
	 * 
	 * @param cookies browser cookies
	 * @return cookie store
	 */
	private BasicCookieStore getWebDriverCookies(Set<Cookie> cookies) {
		BasicCookieStore copyOfWebDriverCookieStore = new BasicCookieStore();
		for (Cookie seleniumCookie : cookies) {
			BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(),
					seleniumCookie.getValue());
			duplicateCookie.setDomain(seleniumCookie.getDomain());
			duplicateCookie.setSecure(seleniumCookie.isSecure());
			duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
			duplicateCookie.setPath(seleniumCookie.getPath());
			copyOfWebDriverCookieStore.addCookie(duplicateCookie);
		}
		return copyOfWebDriverCookieStore;
	}

	/**
	 * Get the web driver user agent.
	 * 
	 * @return web driver user agent
	 */
	private String getWebDriverUserAgent() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return navigator.userAgent").toString();
	}

}
