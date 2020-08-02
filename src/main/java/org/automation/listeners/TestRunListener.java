package org.automation.listeners;

import static org.automation.logger.Log.error;
import static org.automation.logger.Log.info;
import static org.automation.utilities.Screenshot.takeScreenShot;

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
public class TestRunListener implements ITestListener, ISuiteListener {

	@Override
	public void onTestStart(ITestResult result) {
		info("Execution of the test [" + result.getName() + "] started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		info("Test [" + result.getName() + "] passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		error("Test [" + result.getName() + "] failed", result.getThrowable());
		result.setAttribute("failureScreenshot", takeScreenShot("Failure_" + result.getName()));
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		info("Test [" + result.getName() + "] skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		error("Test [" + result.getName() + "] failed within success percentage", result.getThrowable());
	}

	@Override
	public void onStart(ITestContext context) {
		info("About to begin executing Test [" + context.getName() + "]");
	}

	@Override
	public void onFinish(ITestContext context) {
		info("About to end executing Test [" + context.getName() + "]");
	}

	@Override
	public void onStart(ISuite suite) {
		info("About to begin executing Suite [" + suite.getName() + "]");
	}

	@Override
	public void onFinish(ISuite suite) {
		info("About to end executing Suite [" + suite.getName() + "]");
	}

}
