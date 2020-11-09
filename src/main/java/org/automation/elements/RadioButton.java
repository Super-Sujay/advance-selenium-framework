package org.automation.elements;

import static org.automation.logger.Log.info;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import org.openqa.selenium.By;

/**
 * This Class is used to handle <b>Radio Button</b>.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 */
public final class RadioButton extends Element {

	/**
	 * This Constructor is used to create an object to access a <b>RadioButton</b>.
	 * 
	 * @param description description of the RadioButton
	 * @param locator     locator of the RadioButton
	 */
	public RadioButton(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Click on the radio button.
	 */
	public void click() {
		info("Click [" + description + "] radio button");
		wait.until(elementToBeClickable(locator)).click();
	}

	/**
	 * Is the radio button selected.
	 * 
	 * @return true if selected, false otherwise
	 */
	public boolean isSelected() {
		info("Is [" + description + "] radio button selected");
		return wait.until(elementToBeClickable(locator)).isSelected();
	}
}
