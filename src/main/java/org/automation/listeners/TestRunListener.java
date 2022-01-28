package org.automation.listeners;

import static org.automation.config.DriverFactory.getDriver;
import static org.automation.utilities.Screenshot.takeScreenShot;

import org.automation.logger.Log;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Listener class to log the execution details of every test.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 06/11/2020
 *
 */
public final class TestRunListener implements ITestListener, ISuiteListener {

	@Override
	public void onTestStart(ITestResult result) {
		Log.info("Execution of the test [" + result.getName() + "] started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		Log.info("Test [" + result.getName() + "] passed");
		if (Boolean.getBoolean("remoteDriver")) {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript(
					"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		Log.error("Test [" + result.getName() + "] failed", result.getThrowable());
		result.setAttribute("failureScreenshot", takeScreenShot("Failure_" + result.getName()));
		if (Boolean.getBoolean("remoteDriver")) {
			JavascriptExecutor jse = (JavascriptExecutor) getDriver();
			jse.executeScript(
					"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\"}}");
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		Log.info("Test [" + result.getName() + "] skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Log.error("Test [" + result.getName() + "] failed within success percentage", result.getThrowable());
	}

	@Override
	public void onStart(ITestContext context) {
		Log.info("About to begin executing Test [" + context.getName() + "]");
	}

	@Override
	public void onFinish(ITestContext context) {
		Log.info("About to end executing Test [" + context.getName() + "]");
	}

	@Override
	public void onStart(ISuite suite) {
		Log.info("About to begin executing Suite [" + suite.getName() + "]");
	}

	@Override
	public void onFinish(ISuite suite) {
		Log.info("About to end executing Suite [" + suite.getName() + "]");
	}

}
