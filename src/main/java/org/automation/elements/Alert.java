package org.automation.elements;

import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

import org.automation.logger.Log;
import org.automation.utilities.ExplicitWait;
import org.openqa.selenium.TimeoutException;

/**
 * This Class is used to handle <b>Alerts</b>.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 */
public final class Alert {

	private final String description;
	private final ExplicitWait wait;

	/**
	 * This Constructor is used to create an object to access an <b>Alert</b>.
	 * 
	 * @param description description of the Alert
	 */
	public Alert(String description) {
		wait = new ExplicitWait();
		this.description = description;
	}

	/**
	 * To check if an alert is present.
	 * 
	 * @return true if present, false otherwise
	 */
	public boolean isPresent() {
		Log.info("Check for Alert [" + description + "] to be present");
		try {
			wait.until(alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			Log.info("Alert [" + description + "] is not present");
			return false;
		}
	}

	/**
	 * Accept the alert.
	 */
	public void accept() {
		Log.info("Accept the [" + description + "] alert");
		wait.until(alertIsPresent()).accept();
	}

	/**
	 * Dismiss the alert.
	 */
	public void dismiss() {
		Log.info("Dismiss the [" + description + "] alert");
		wait.until(alertIsPresent()).dismiss();
	}

	/**
	 * Get the text present in an alert.
	 * 
	 * @return the text
	 */
	public String getText() {
		Log.info("Get the text from the [" + description + "] alert");
		return wait.until(alertIsPresent()).getText();
	}

	/**
	 * Enter text in the alert.
	 * 
	 * @param textToEnter text to enter
	 */
	public void enterText(String textToEnter) {
		Log.info("Enter text [" + textToEnter + "] in the [" + description + "] text box");
		wait.until(alertIsPresent()).sendKeys(textToEnter);
	}

}
