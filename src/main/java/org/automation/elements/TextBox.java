package org.automation.elements;

import static org.automation.logger.Log.info;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * This Class is used to handle <b>Text Box</b>.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 */
public final class TextBox extends Element {

	/**
	 * This Constructor is used to create an object to access a <b>Text Box</b>.
	 * 
	 * @param description description of the text box
	 * @param locator     locator of the text box
	 */
	public TextBox(String description, By locator) {
		super(description, locator);
	}

	/**
	 * Enter text in the text box.
	 * 
	 * @param textToEnter text to enter
	 */
	public void enterText(String textToEnter) {
		if (description.toLowerCase().contains("password"))
			info("Enter text [********] in the [" + description + "] text box");
		else
			info("Enter text [" + textToEnter + "] in the [" + description + "] text box");
		WebElement element = wait.until(elementToBeClickable(locator));
		element.clear();
		element.sendKeys(textToEnter);
	}

	/**
	 * Get the text from the text box.
	 * 
	 * @return text
	 */
	public String getText() {
		info("Get text from [" + description + "] text box");
		return wait.until(elementToBeClickable(locator)).getText();
	}

}
