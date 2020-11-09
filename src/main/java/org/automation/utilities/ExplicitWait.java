package org.automation.utilities;

import static org.automation.config.DriverFactory.getDriver;

import java.util.function.Function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * To handle conditions that require synchronization.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 08/31/2020
 *
 */
public final class ExplicitWait {

	private long timeout, polling;
	private Wait<WebDriver> wait;

	/**
	 * Create wait object with default timeout and polling interval.
	 */
	public ExplicitWait() {
		timeout = 20;
		polling = 250;
		wait = new WebDriverWait(getDriver(), timeout, polling);
	}

	/**
	 * Create wait object with specified timeout and polling interval.
	 * 
	 * @param timeout time to wait
	 * @param polling poll interval
	 */
	public ExplicitWait(long timeout, long polling) {
		wait = new WebDriverWait(getDriver(), timeout, polling);
	}

	/**
	 * Wait until the specified expected condition is met.
	 * 
	 * @param <R>               the output to return
	 * @param expectedCondition condition to wait
	 * @return output
	 */
	public <R> R until(Function<WebDriver, R> expectedCondition) {
		return wait.until(expectedCondition);
	}

}
