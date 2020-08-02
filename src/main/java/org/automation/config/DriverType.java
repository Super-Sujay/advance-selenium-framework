package org.automation.config;

import static org.openqa.selenium.remote.CapabilityType.PROXY;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * To handle instances of all the driver types.
 * 
 * @author Sujay Sawant
 * @version 1.0.0
 * @since 6/11/2020
 *
 */
public enum DriverType implements DriverSetup {

	/**
	 * This is the Chrome browser driver implementation.
	 */
	CHROME {

		@Override
		public WebDriver getWebDriverObject(Capabilities desiredCapabilities) {
			ChromeOptions options = new ChromeOptions();
			options.merge(desiredCapabilities);
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver(options);
		}

		@Override
		public DesiredCapabilities getCapabilities(Proxy proxySetting) {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			return addProxySettings(capabilities, proxySetting);
		}

	},

	/**
	 * This is the Internet Explorer browser driver implementation.
	 */
	IE {

		@Override
		public WebDriver getWebDriverObject(Capabilities desiredCapabilities) {
			InternetExplorerOptions options = new InternetExplorerOptions(desiredCapabilities);
			WebDriverManager.iedriver().setup();
			return new InternetExplorerDriver(options);
		}

		@Override
		public DesiredCapabilities getCapabilities(Proxy proxySetting) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, UnexpectedAlertBehaviour.ACCEPT);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			return addProxySettings(capabilities, proxySetting);
		}

	},

	/**
	 * This is the Firefor browser driver implementation.
	 */
	FIREFOX {

		@Override
		public WebDriver getWebDriverObject(Capabilities desiredCapabilities) {
			FirefoxOptions options = new FirefoxOptions(desiredCapabilities);
			WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver(options);
		}

		@Override
		public DesiredCapabilities getCapabilities(Proxy proxySetting) {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			return addProxySettings(capabilities, proxySetting);
		}

	},

	/**
	 * This is the Safari browser driver implementation.
	 */
	SAFARI {

		@Override
		public WebDriver getWebDriverObject(Capabilities desiredCapabilities) {
			SafariOptions options = new SafariOptions(desiredCapabilities);
			return new SafariDriver(options);
		}

		@Override
		public DesiredCapabilities getCapabilities(Proxy proxySetting) {
			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			return addProxySettings(capabilities, proxySetting);
		}

	},

	/**
	 * This is the Opera browser driver implementation.
	 */
	OPERA {

		@Override
		public WebDriver getWebDriverObject(Capabilities desiredCapabilities) {
			OperaOptions options = new OperaOptions();
			options.merge(desiredCapabilities);
			WebDriverManager.operadriver().setup();
			return new OperaDriver(options);
		}

		@Override
		public DesiredCapabilities getCapabilities(Proxy proxySetting) {
			DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
			return addProxySettings(capabilities, proxySetting);
		}

	};

	/**
	 * Add proxy settings to the desired capabilities.
	 * 
	 * @param capabilities
	 *            desired capabilities
	 * @param proxySetting
	 *            proxy settings
	 * @return desired capabilities
	 */
	protected DesiredCapabilities addProxySettings(DesiredCapabilities capabilities, Proxy proxySetting) {
		if (proxySetting != null)
			capabilities.setCapability(PROXY, proxySetting);
		return capabilities;
	}

}
