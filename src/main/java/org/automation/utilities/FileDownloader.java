package org.automation.utilities;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.http.impl.client.HttpClientBuilder.create;
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
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class FileDownloader {

	private WebDriver driver;
	private RequestType httpRequestMethod = GET;
	private URI fileURI;
	private List<NameValuePair> urlParameters;

	public FileDownloader(WebDriver driver) {
		this.driver = driver;
	}

	public void setHttpRequestMethod(RequestType requestType) {
		httpRequestMethod = requestType;
	}

	public void setUrlParameters(List<NameValuePair> urlParameters) {
		this.urlParameters = urlParameters;
	}

	public void setURI(URI linkToFile) {
		fileURI = linkToFile;
	}

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

	public String getLinkHttpData(String extension) throws IOException {
		HttpResponse downloadableFile = makeHttpConnection();
		String data = "Unable to get Data for " + extension + " extension!";
		switch (extension) {
		case ".txt":
			try {
				data = EntityUtils.toString(downloadableFile.getEntity());
			} finally {
				downloadableFile.getEntity().getContent().close();
			}
			return data;
		case ".pdf":
			try {
				if (downloadableFile.getEntity().getContentType().getValue().equals("application/pdf")) {
					PDDocument document = PDDocument.load(downloadableFile.getEntity().getContent());
					if (document.isEncrypted())
						data = "PDF Document is encrypted";
					else
						data = new PDFTextStripper().getText(document);
					document.close();
				}
			} finally {
				downloadableFile.getEntity().getContent().close();
			}
			return data;
		default:
			return data;
		}
	}

	private HttpResponse makeHttpConnection() throws IOException {
		if (fileURI == null)
			throw new NullPointerException("No file URI specified");
		HttpClient client = create().build();
		HttpRequestBase requestMethod = httpRequestMethod.getRequestMethod();
		requestMethod.setURI(fileURI);
		BasicHttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(HttpClientContext.COOKIE_STORE, getWebDriverCookies(driver.manage().getCookies()));
		requestMethod.setHeader("User-Agent", getWebDriverUserAgent());
		if (urlParameters != null
				&& (httpRequestMethod.equals(PATCH) || httpRequestMethod.equals(POST) || httpRequestMethod.equals(PUT)))
			((HttpEntityEnclosingRequestBase) requestMethod).setEntity(new UrlEncodedFormEntity(urlParameters));
		return client.execute(requestMethod, localContext);
	}

	private BasicCookieStore getWebDriverCookies(Set<Cookie> cookies) {
		BasicCookieStore copyOfWebDriverCookieStore = new BasicCookieStore();
		cookies.forEach(seleniumCookie -> {
			BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(),
					seleniumCookie.getValue());
			duplicateCookie.setDomain(seleniumCookie.getDomain());
			duplicateCookie.setSecure(seleniumCookie.isSecure());
			duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
			duplicateCookie.setPath(seleniumCookie.getPath());
			copyOfWebDriverCookieStore.addCookie(duplicateCookie);
		});
		return copyOfWebDriverCookieStore;
	}

	private String getWebDriverUserAgent() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return navigator.userAgent").toString();
	}

}
