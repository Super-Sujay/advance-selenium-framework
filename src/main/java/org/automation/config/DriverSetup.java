package org.automation.config;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * To provide a setup for driver instance.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 6/11/2020
 *
 */
public interface DriverSetup {

	/**
	 * Get the web driver object with the specified capabilities.
	 * 
	 * @param desiredCapabilities capabilities
	 * @return web driver instance
	 */
	WebDriver getWebDriverObject(Capabilities desiredCapabilities);

	/**
	 * Get the desired capabilites with the specified proxy settings.
	 * 
	 * @param proxySetting proxy settings
	 * @return desired capabilities
	 */
	DesiredCapabilities getCapabilities(Proxy proxySetting);

}
