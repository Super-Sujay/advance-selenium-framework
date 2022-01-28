package org.automation.config;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

import org.automation.logger.Log;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;

/**
 * To create a thread safe web driver instances.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 6/11/2020
 *
 */
public final class WebDriverThread {

	private WebDriver driver;
	private DriverType selectedDriverType;
	private BrowserMobProxy browserMobProxy;
	private boolean usingBrowserMobProxy;

	private final DriverType defaultDriverType = DriverType.CHROME;
	private final Optional<String> browser = Optional.ofNullable(System.getProperty("browser"));
	private final String operatingSystem = System.getProperty("os.name").toUpperCase();
	private final String systemArchitecture = System.getProperty("os.arch").toUpperCase();
	private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");
	private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");
	private final String proxyHostname = System.getProperty("proxyHost");
	private final Integer proxyPort = Integer.getInteger("proxyPort");
	private final String proxyDetails = String.format("%s:%d", proxyHostname, proxyPort);

	private static final LocalDateTime TIMESTAMP = LocalDateTime.now();

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
		if (usingBrowserMobProxy) {
			return browserMobProxy;
		}
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
	 * @param useBrowserMobProxy true to use browser mob proxy, false otherwise
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
					if (proxyEnabled) {
						browserMobProxy.setChainedProxy(new InetSocketAddress(proxyHostname, proxyPort));
					}
					proxy = ClientUtil.createSeleniumProxy(browserMobProxy);
				} else {
					proxy = new Proxy();
					proxy.setProxyType(ProxyType.MANUAL);
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
			Log.warn("Issues initializing driver, defaulting to '" + driverType + "'...");
		}
		return driverType;
	}

	/**
	 * Create a new web driver instance.
	 * 
	 * @param capabilities desired capabilities to use
	 */
	private void instantiateWebDriver(DesiredCapabilities capabilities) {
		Log.info("Operating System: " + operatingSystem);
		Log.info("System Architecture: " + systemArchitecture);
		Log.info("Browser Selection: " + selectedDriverType);
		if (useRemoteWebDriver) {
			URL seleniumGridURL = null;
			try {
				seleniumGridURL = new URL(System.getProperty("gridURL"));
			} catch (MalformedURLException e) {
				Log.error("Incorrect Grid URL...", e);
				throw new RuntimeException("Incorrect Grid URL...", e);
			}
			String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
			String desiredPlatform = System.getProperty("desiredPlatform");
			if (desiredPlatform != null && !desiredPlatform.isEmpty()) {
				capabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
			}
			if (desiredBrowserVersion != null && !desiredBrowserVersion.isEmpty()) {
				capabilities.setVersion(desiredBrowserVersion);
			}
			capabilities.setCapability("project", "Framework Testing");
			capabilities.setCapability("build", "Build-" + TIMESTAMP);
			capabilities.setCapability("browserstack.debug", true);
			capabilities.setCapability("browserstack.networkLogs", true);
			capabilities.setCapability("browserstack.user", System.getenv("BROWSERSTACK_USERNAME"));
			capabilities.setCapability("browserstack.key", System.getenv("BROWSERSTACK_ACCESS_KEY"));
			driver = new RemoteWebDriver(seleniumGridURL, capabilities);
		} else {
			driver = selectedDriverType.getWebDriverObject(capabilities);
		}
	}

}
