package org.automation.utilities;

import static org.automation.config.DriverFactory.getDriver;

import java.util.function.Function;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExplicitWait {

	private long timeout, polling;
	private Wait<WebDriver> wait;

	public ExplicitWait() {
		timeout = 20;
		polling = 250;
		wait = new WebDriverWait(getDriver(), timeout, polling);
	}

	public ExplicitWait(long timeout, long polling) {
		wait = new WebDriverWait(getDriver(), timeout, polling);
	}

	public <R> R until(Function<WebDriver, R> expectedCondition) {
		return wait.until(expectedCondition);
	}

}
