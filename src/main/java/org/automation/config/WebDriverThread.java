package org.automation.config;

import static java.lang.Boolean.getBoolean;
import static java.lang.Integer.getInteger;
import static java.lang.System.getProperty;
import static java.util.Optional.ofNullable;
import static net.lightbody.bmp.client.ClientUtil.createSeleniumProxy;
import static org.automation.config.DriverType.CHROME;
import static org.automation.logger.Log.error;
import static org.automation.logger.Log.info;
import static org.automation.logger.Log.warn;
import static org.openqa.selenium.Platform.valueOf;
import static org.openqa.selenium.Proxy.ProxyType.MANUAL;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;

/**
 * To create a thread safe web driver instances.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 6/11/2020
 *
 */
public class WebDriverThread {

	private WebDriver driver;
	private DriverType selectedDriverType;
	private BrowserMobProxy browserMobProxy;
	private boolean usingBrowserMobProxy;

	private final DriverType defaultDriverType = CHROME;
	private final Optional<String> browser = ofNullable(getProperty("browser"));
	private final String operatingSystem = getProperty("os.name").toUpperCase();
	private final String systemArchitecture = getProperty("os.arch").toUpperCase();
	private final boolean useRemoteWebDriver = getBoolean("remoteDriver");
	private final boolean proxyEnabled = getBoolean("proxyEnabled");
	private final String proxyHostname = getProperty("proxyHost");
	private final Integer proxyPort = getInteger("proxyPort");
	private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);

	/**
	 * Get the web driver instance.
	 * 
	 * @return web driver instance
	 */
	public WebDriver getDriver() {
		return getDriver(usingBrowserMobProxy);
	}

	/**
	 * Get the browser mob proxy enabled web driver instance.
	 * 
	 * @return browser mob proxy enabled web driver instance
	 */
	public WebDriver getBrowserMobProxyEnabledDriver() {
		return getDriver(true);
	}

	/**
	 * Get the browser mob proxy
	 * 
	 * @return browser mob proxy
	 */
	public BrowserMobProxy getBrowserMobProxy() {
		if (usingBrowserMobProxy)
			return browserMobProxy;
		return null;
	}

	/**
	 * Quit the web driver instance.
	 */
	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	/**
	 * Get the web driver instance.
	 * 
	 * @param useBrowserMobProxy
	 *            true to use browser mob proxy, false otherwise
	 * @return web driver instance
	 */
	private WebDriver getDriver(boolean useBrowserMobProxy) {
		if (driver != null && usingBrowserMobProxy != useBrowserMobProxy) {
			driver.quit();
			driver = null;
		}
		if (driver == null) {
			Proxy proxy = null;
			if (proxyEnabled || useBrowserMobProxy) {
				if (useBrowserMobProxy) {
					usingBrowserMobProxy = true;
					browserMobProxy = new BrowserMobProxyServer();
					browserMobProxy.start();
					if (proxyEnabled)
						browserMobProxy.setChainedProxy(new InetSocketAddress(proxyHostname, proxyPort));
					proxy = createSeleniumProxy(browserMobProxy);
				} else {
					proxy = new Proxy();
					proxy.setProxyType(MANUAL);
					proxy.setHttpProxy(proxyDetails);
					proxy.setSslProxy(proxyDetails);
				}
			}
			selectedDriverType = determineEffectiveDriverType();
			DesiredCapabilities capabilities = selectedDriverType.getCapabilities(proxy);
			instantiateWebDriver(capabilities);
		}
		return driver;
	}

	/**
	 * Get the browser's driver type to use.
	 * 
	 * @return browser's driver type
	 */
	private DriverType determineEffectiveDriverType() {
		DriverType driverType = defaultDriverType;
		try {
			driverType = DriverType.valueOf(browser.orElse("No browser specified").toUpperCase());
		} catch (IllegalArgumentException e) {
			warn("Issues initializing driver, defaulting to '" + driverType + "'...");
		}
		return driverType;
	}

	/**
	 * Create a new web driver instance.
	 * 
	 * @param capabilities
	 *            desired capabilities to use
	 */
	private void instantiateWebDriver(DesiredCapabilities capabilities) {
		info("Operating System: " + operatingSystem);
		info("System Architecture: " + systemArchitecture);
		info("Browser Selection: " + selectedDriverType);
		if (useRemoteWebDriver) {
			URL seleniumGridURL = null;
			try {
				seleniumGridURL = new URL(getProperty("gridURL"));
			} catch (MalformedURLException e) {
				error("Incorrect Grid URL...", e);
				e.printStackTrace();
			}
			String desiredBrowserVersion = getProperty("desiredBrowserVersion");
			String desiredPlatform = getProperty("desiredPlatform");
			if (desiredPlatform != null && !desiredPlatform.isEmpty())
				capabilities.setPlatform(valueOf(desiredPlatform.toUpperCase()));
			if (desiredBrowserVersion != null && !desiredBrowserVersion.isEmpty())
				capabilities.setVersion(desiredBrowserVersion);
			driver = new RemoteWebDriver(seleniumGridURL, capabilities);
		} else
			driver = selectedDriverType.getWebDriverObject(capabilities);
	}

}
