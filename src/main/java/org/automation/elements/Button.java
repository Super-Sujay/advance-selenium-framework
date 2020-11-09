package org.automation.elements;

import static org.automation.logger.Log.error;
import static org.automation.logger.Log.info;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import org.automation.base.BasePage;
import org.openqa.selenium.By;

/**
 * This Class is used to handle <b>Button</b>.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 */
public final class Button extends Element {

	/**
	 * This Constructor is used to create an object to access a <b>Button</b>.
	 * 
	 * @param description description of the Button
	 * @param locator     locator of the Button
	 */
	public Button(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Click on the button.
	 */
	public void click() {
		info("Click [" + description + "] button");
		wait.until(elementToBeClickable(locator)).click();
	}

	/**
	 * Click on the button.
	 * 
	 * @param <T>       the type of the page class
	 * @param pageClass expected class of the page after the click
	 * @return the pageClass object
	 */
	public <T extends BasePage> T click(Class<T> pageClass) {
		info("Click [" + description + "] button");
		try {
			wait.until(elementToBeClickable(locator)).click();
			return pageClass.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			error("Unable to create instance of the page class", e);
			throw new RuntimeException("Unable to create instance of the page class", e);
		}
	}

}
