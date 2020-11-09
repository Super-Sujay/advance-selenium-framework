package org.automation.elements;

import static org.automation.logger.Log.info;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * This Class is used to handle <b>Check Box</b>.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 */
public final class CheckBox extends Element {

	/**
	 * This Constructor is used to create an object to access a <b>CheckBox</b>.
	 * 
	 * @param description description of the check box
	 * @param locator     locator of the check box
	 */
	public CheckBox(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Check the check box.
	 */
	public void check() {
		WebElement element = wait.until(elementToBeClickable(locator));
		if (element.isSelected()) {
			info("Checkbox [" + description + "] is already checked");
		} else {
			info("Check [" + description + "] checkbox");
			element.click();
		}
	}

	/**
	 * Un-check the check box.
	 */
	public void uncheck() {
		WebElement element = wait.until(elementToBeClickable(locator));
		if (element.isSelected()) {
			info("Uncheck [" + description + "] checkbox");
			element.click();
		} else {
			info("Checkbox [" + description + "] is already unchecked");
		}
	}

	/**
	 * Is the check box checked.
	 * 
	 * @return true if checked, false if un-checked
	 */
	public boolean isChecked() {
		info("Is [" + description + "] checkbox checked");
		return wait.until(elementToBeClickable(locator)).isSelected();
	}

}
