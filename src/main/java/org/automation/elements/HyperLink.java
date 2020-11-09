package org.automation.elements;

import static org.automation.config.DriverFactory.getDriver;
import static org.automation.logger.Log.error;
import static org.automation.logger.Log.info;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.automation.base.BasePage;
import org.automation.utilities.FileDownloader;
import org.automation.utilities.RequestType;
import org.openqa.selenium.By;

/**
 * This Class is used to handle <b>Hyperlink</b>.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 */
public final class HyperLink extends Element {

	/**
	 * This Constructor is used to create an object to access a <b>Hyperlink</b>.
	 * 
	 * @param description description of the hyper link
	 * @param locator     locator of the hyper link
	 */
	public HyperLink(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Click on the hyper link.
	 */
	public void click() {
		info("Click [" + description + "] link");
		wait.until(elementToBeClickable(locator)).click();
	}

	/**
	 * Click on the hyper link.
	 * 
	 * @param <T>       the type of the page class
	 * @param pageClass expected class of the page after the click
	 * @return the pageClass object
	 */
	public <T extends BasePage> T click(Class<T> pageClass) {
		info("Click [" + description + "] link");
		try {
			wait.until(elementToBeClickable(locator)).click();
			return pageClass.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			error("Unable to create instance of the page class [" + pageClass.getSimpleName() + "]", e);
			throw new RuntimeException(
					"Unable to create instance of the page class [" + pageClass.getSimpleName() + "]", e);
		}
	}

	/**
	 * Get the HTTP Status of the link.
	 * 
	 * @return HTTP Status
	 */
	public int httpStatus() {
		info("Get the HTTP Link Status from the [" + description + "] link");
		try {
			FileDownloader downloadHandler = new FileDownloader(getDriver());
			URI fileAsURI = new URI(super.getAttributeValue("href"));
			downloadHandler.setURI(fileAsURI);
			downloadHandler.setHttpRequestMethod(RequestType.GET);
			return downloadHandler.getLinkHttpStatus();
		} catch (URISyntaxException | IOException e) {
			error("Unable to get the HTTP Link Status from [" + description + "] link", e);
			return 500;
		}
	}

	/**
	 * Download file from the link.
	 * 
	 * @param extension extension of the downloaded file
	 * @return downloaded file
	 */
	public File downloadedFile(String extension) {
		info("Download the file from the [" + description + "] link");
		try {
			FileDownloader downloadHandler = new FileDownloader(getDriver());
			URI fileAsURI = new URI(super.getAttributeValue("href"));
			downloadHandler.setURI(fileAsURI);
			downloadHandler.setHttpRequestMethod(RequestType.GET);
			return downloadHandler.downloadFile(extension);
		} catch (URISyntaxException | IOException e) {
			error("Unable to download file present in the [" + description + "] link", e);
			return null;
		}

	}

	/**
	 * Get the data present in the file downloaded from the link.
	 * 
	 * @param extension extension of the downloaded file
	 * @return file data
	 */
	public String fileData(String extension) {
		info("Get the Data present in the file from the [" + description + "] link");
		try {
			FileDownloader downloadHandler = new FileDownloader(getDriver());
			URI fileAsURI = new URI(super.getAttributeValue("href"));
			downloadHandler.setURI(fileAsURI);
			downloadHandler.setHttpRequestMethod(RequestType.GET);
			return downloadHandler.getLinkHttpData(extension);
		} catch (URISyntaxException | IOException e) {
			error("Unable to get the Data present in the file from the [" + description + "] link", e);
			return "Unable to get the Data present in the file from the [" + description + "] link";
		}
	}

}
