package org.automation.base;

import static org.automation.config.DriverFactory.getDriver;
import static org.automation.logger.Log.error;
import static org.automation.logger.Log.info;
import static org.automation.utilities.CheckFileHash.generateHashForFileOfType;

import java.io.File;

import org.automation.elements.Element;
import org.automation.utilities.HashType;
import org.openqa.selenium.NoSuchWindowException;

/**
 * To extend every page class created.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 6/11/2020
 *
 */
public abstract class BasePage {

	private String parentWindow;

	/**
	 * Open the specified URL.
	 * 
	 * @param url URL to open
	 */
	protected void openUrl(String url) {
		info("Open the URL [" + url + "]");
		getDriver().get(url);
	}

	/**
	 * Get the URL of the current page.
	 * 
	 * @return page URL
	 */
	protected String getPageUrl() {
		info("Get the Current URL");
		return getDriver().getCurrentUrl();
	}

	/**
	 * Get the Title of the current page.
	 * 
	 * @return page title
	 */
	protected String getPageTitle() {
		info("Get the Current Page Title");
		return getDriver().getTitle();
	}

	/**
	 * Refresh the current page.
	 */
	protected void refreshPage() {
		info("Refresh the browser");
		getDriver().navigate().refresh();
	}

	/**
	 * Get the Hash String of the specified file.
	 * 
	 * @param file     file whose hash is needed
	 * @param hashType The hash type of the file
	 * @return The hash of the specified file
	 */
	protected String getFileHash(File file, HashType hashType) {
		info("Get the hash of the [" + file.getName() + "] file");
		try {
			return generateHashForFileOfType(file, hashType);
		} catch (RuntimeException e) {
			error("Unable to get the hash of the [" + file.getName() + "] file", e);
			return e.getMessage();
		}
	}

	/**
	 * Switch to the newly opened window.
	 * 
	 * @param description description of the new window
	 */
	protected void switchToWindow(String description) {
		info("Switch to window [" + description + "]");
		parentWindow = getDriver().getWindowHandle();
		for (String windowHandle : getDriver().getWindowHandles())
			if (!windowHandle.equals(parentWindow))
				getDriver().switchTo().window(windowHandle);
	}

	/**
	 * Switch to the window containing the specified URL text.
	 * 
	 * @param description description of the new window
	 * @param urlText     URL text that the window contains
	 */
	protected void switchToWindowContainingUrlText(String description, String urlText) {
		info("Switch to window [" + description + "] which contains URL text [" + urlText + "]");
		parentWindow = getDriver().getWindowHandle();
		getDriver().getWindowHandles().stream().map(getDriver().switchTo()::window)
				.filter(driver -> driver.getCurrentUrl().contains(urlText)).findFirst()
				.orElseThrow(() -> new NoSuchWindowException(
						"Unable to find window [" + description + "] which contains URL text [" + urlText + "]"));
	}

	/**
	 * Switch to the window containing the specified title.
	 * 
	 * @param description description of the new window
	 * @param title       title that the window contains
	 */
	protected void switchToWindowContainingTitle(String description, String title) {
		info("Switch to window [" + description + "] which contains title [" + title + "]");
		parentWindow = getDriver().getWindowHandle();
		getDriver().getWindowHandles().stream().map(getDriver().switchTo()::window)
				.filter(driver -> driver.getTitle().contains(title)).findFirst()
				.orElseThrow(() -> new NoSuchWindowException(
						"Unable to find window [" + description + "] which contains title [" + title + "]"));
	}

	/**
	 * Switch to the Main window.
	 * 
	 * @param description description of the main window
	 */
	protected void switchToParentWindow(String description) {
		info("Switch to parent window [" + description + "]");
		getDriver().switchTo().window(parentWindow);
	}

	/**
	 * Switch to the frame containing the specified element.
	 * 
	 * @param description description of the frame
	 * @param element     element of the frame
	 */
	protected void switchToFrame(Element element) {
		info("Switch to frame [" + element.getDescription() + "]");
		getDriver().switchTo().frame(element.getWebElement());
	}

	/**
	 * Switch to the frame containing the specified name or ID.
	 * 
	 * @param description description of the frame
	 * @param nameOrId    name or ID of the frame
	 */
	protected void switchToFrame(String description, String nameOrId) {
		info("Switch to frame [" + description + "]");
		getDriver().switchTo().frame(nameOrId);
	}

	/**
	 * Switch to the frame containing the specified index number.
	 * 
	 * @param description description of the frame
	 * @param index       index number of the frame
	 */
	protected void switchToFrame(String description, int index) {
		info("Switch to frame [" + description + "]");
		getDriver().switchTo().frame(index);
	}

	/**
	 * Switch to the default window.
	 * 
	 * @param description description of the window
	 */
	protected void switchToDefaultContent(String description) {
		info("Switch to main window [" + description + "]");
		getDriver().switchTo().defaultContent();
	}

}
