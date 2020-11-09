package org.automation.elements;

import static org.automation.config.DriverFactory.getDriver;
import static org.automation.logger.Log.error;
import static org.automation.logger.Log.info;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.automation.base.BasePage;
import org.automation.utilities.FileDownloader;
import org.automation.utilities.RequestType;
import org.openqa.selenium.By;

/**
 * This Class is used to handle <b>Image</b>.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 */
public final class Image extends Element {

	/**
	 * This Constructor is used to create an object to access a <b>Button</b>.
	 * 
	 * @param description description of the Button
	 * @param locator     locator of the Button
	 */
	public Image(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Click on the image.
	 */
	public void click() {
		info("Click [" + description + "] link");
		wait.until(elementToBeClickable(locator)).click();
	}

	/**
	 * Click on the image.
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
	 * Check whether the image is available.
	 * 
	 * @return true if image is available, false otherwise
	 */
	public boolean isAvailable() {
		info("Check whether the [" + description + "] image is present");
		try {
			FileDownloader downloadHandler = new FileDownloader(getDriver());
			URI fileAsURI = new URI(getAttributeValue("src"));
			downloadHandler.setURI(fileAsURI);
			downloadHandler.setHttpRequestMethod(RequestType.GET);
			return downloadHandler.getLinkHttpStatus() == 200;
		} catch (URISyntaxException | IOException e) {
			error("Unable to get the Link Status from [" + description + "] image", e);
			return false;
		}
	}

}
