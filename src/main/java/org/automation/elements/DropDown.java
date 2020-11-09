package org.automation.elements;

import static org.automation.logger.Log.info;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * This Class is used to handle <b>Drop Down</b>.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 */
public final class DropDown extends Element {

	/**
	 * This Constructor is used to create an object to access a <b>Drop Down</b>.
	 * 
	 * @param description description of the drop down
	 * @param locator     locator of the drop down
	 */
	public DropDown(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Select the drop down option by index.
	 * 
	 * @param index the index to be selected
	 */
	public void selectByIndex(int index) {
		info("Select index [" + index + "] from the [" + description + "] drop down");
		WebElement element = wait.until(elementToBeClickable(locator));
		new Select(element).selectByIndex(index);
	}

	/**
	 * Select the drop down option by value.
	 * 
	 * @param value the value to be selected
	 */
	public void selectByValue(String value) {
		info("Select value [" + value + "] from the [" + description + "] drop down");
		WebElement element = wait.until(elementToBeClickable(locator));
		new Select(element).selectByValue(value);
	}

	/**
	 * Select the drop down option by text.
	 * 
	 * @param visibleText the text to be selected
	 */
	public void selectByVisibleText(String visibleText) {
		info("Select text [" + visibleText + "] from the [" + description + "] drop down");
		WebElement element = wait.until(elementToBeClickable(locator));
		new Select(element).selectByVisibleText(visibleText);
	}

	/**
	 * Get the number of options present in the drop down.
	 * 
	 * @return number of options
	 */
	public int getNumberOfOptions() {
		info("Get the number of options from the [" + description + "] drop down");
		WebElement element = wait.until(elementToBeClickable(locator));
		return new Select(element).getOptions().size();
	}

	/**
	 * Get the list of all the options present in the drop down.
	 * 
	 * @return list of all the options
	 */
	public String[] getAllOptions() {
		info("Get all the options from the [" + description + "] drop down");
		WebElement element = wait.until(elementToBeClickable(locator));
		return new Select(element).getOptions().stream().map(WebElement::getText).toArray(String[]::new);
	}

	/**
	 * Get the currently selected option in the drop down.
	 * 
	 * @return currently selected option
	 */
	public String getSelectedOption() {
		info("Get the selected option from the [" + description + "] drop down");
		WebElement element = wait.until(elementToBeClickable(locator));
		return new Select(element).getFirstSelectedOption().getText();
	}

}
