/**
 * The GNU GENERAL PUBLIC LICENSE (GPLv3)
 *  
 * Copyright (C) 2018  Francisco José Fernández González, Estefanía Fernández Muñoz
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.estefafdez.appium.java.config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.estefafdez.appium.java.utils.AppiumServerHandler;
import org.estefafdez.appium.java.utils.CustomAssertHandler;
import org.estefafdez.appium.java.utils.CustomErrorException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;

/**
 * <p>
 * This class is to configure all the Test Configuration.
 * </p>
 * 
 * @author Francisco José Fernández González<br>
 * <a href="mailto:ffgonzalez1989@gmail.com">ffgonzalez1989@gmail.com</a><br>
 * <a href="https://github.com/FJFGonzalez">https://github.com/FJFGonzalez</a><br>
 * <br><br>
 * @author Estefanía Fernández Muñoz<br>
 * <a href="mailto:estefafdez@gmail.com">estefafdez@gmail.com</a><br>
 * <a href="https://github.com/estefafdez">https://github.com/estefafdez</a><br>
 *
 */
public abstract class TestSetConfig {

	/**
	 * Make the Driver static. This allows it to be created only once and used
	 * across all of the test classes.
	 */
	public static AppiumDriver driver;

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(TestSetConfig.class);

	/** Create the Desired Capabilities. */
	private static DesiredCapabilities caps;

	/** Handler to access to the properties matrix */
	private static PropertiesManager handler = PropertiesManager.getInstance();

	/*--------------------------------------------------------------------* 
	|		LIFE CYCLE												
	*---------------------------------------------------------------------*/

	/**
	 * This method runs before any other method.
	 */
	@BeforeSuite
	protected void setUpConfiguration() {
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
		LOGGER.info("[ Setup Configuration ] - Initializing Setup Configuration");
		try {
			setUpConfigurationProperty();
			setUpAppiumServer();
			setUpLevelLogger();
			setUpCapabilities();
			setUpDriver();
		} catch (CustomErrorException ex) {
			CustomAssertHandler.handlerError("[ Setup Configuration ] - Error on Setup Configuration", ex);
		}
	}

	@BeforeMethod
	protected void beforeAllConfiguration(Method method) {
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
		LOGGER.info("[ Test Status ] - Running Test: " + method.getName());
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
	}

	/**
	 * Method to add/remove from the Failed Suite using the Test Status
	 * 
	 * @param result
	 *            the test result
	 */
	@AfterMethod
	protected void afterAllIsSaidAndDone(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		if (result.getStatus() == ITestResult.FAILURE) {
			LOGGER.info(ConstantConfig.LOG_SEPARATOR);
			LOGGER.info("[ Test Status ] - The execution of the Test " + testName + " was FAILURE");
			this.captureScreenShots(result);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			LOGGER.info(ConstantConfig.LOG_SEPARATOR);
			LOGGER.info("[ Test Status ] - The execution of the Test " + testName + " was SUCCESS");
		}

		LOGGER.info("[ Test Status ] - Reset the currently running App for this session");
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
		String appId = (String) caps.getCapability(handler.getConfigValueFromMatrix(ConstantConfig.PLATFORM_NAME).equalsIgnoreCase(MobilePlatform.ANDROID) ? "appium:appPackage" : "appium:bundleId");
		((InteractsWithApps) driver).terminateApp(appId);
		((InteractsWithApps) driver).activateApp(appId);
	}

	/**
	 * Method to quit Appium. Always remember to quit.
	 */
	@AfterSuite
	protected void tearDownAppium() {
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
		LOGGER.info("[ Driver Configuration ] - Unistalling the current running App");
		if ((MobilePlatform.ANDROID).equalsIgnoreCase(handler.getConfigValueFromMatrix(ConstantConfig.PLATFORM_NAME))) {
			((InteractsWithApps) driver).removeApp((String) caps.getCapability("appium:appPackage"));
		}
		else {
			((InteractsWithApps) driver).removeApp((String) caps.getCapability("appium:bundleId"));
		}
		
		LOGGER.info("[ Driver Configuration ] - Quit this Driver, closing every instance associated");
		driver.quit();
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
		AppiumServerHandler.appiumServerStop();
	}

	/*--------------------------------------------------------------------* 
	|		SETUPS												
	*---------------------------------------------------------------------*/

	/**
	 * Method to set up the Appium Server.
	 * 
	 * @throws CustomErrorException
	 */
	private void setUpAppiumServer() throws CustomErrorException {
		AppiumServerHandler.appiumServerStart();
	}

	/**
	 * Method to set up the first configuration property file
	 * 
	 * @throws CustomErrorException
	 */
	private void setUpConfigurationProperty() throws CustomErrorException {
		PropertiesManager.getInstance().loadPropertiesMatrix(ConstantConfig.CONFIG_FILE_PROP);
	}

	/**
	 * Method to set the Level Log. [ALL > DEBUG > INFO > WARN > ERROR > FATAL > OFF]
	 */
	private void setUpLevelLogger() {
		Level logLevelDefault = Level.INFO;

		/** LOG Level */
		String logLevel = handler.getConfigValueFromMatrix(ConstantConfig.LOG_LEVEL);

		LOGGER.info("[ System Properties ] - Setting Log Level");

		if (logLevel == null || logLevel.isEmpty()) {
			Configurator.setRootLevel(logLevelDefault);
			LOGGER.info("[ System Properties ] - There is not a defined Log Level, we are using the default: "
					+ logLevelDefault);
		} else {
			Configurator.setRootLevel(Level.getLevel(logLevel));
			LOGGER.info("[ System Properties ] - Log Level stablished on: " + logLevel);
		}
	}
	
	/**
	 * Method to setUp the Driver.
	 * 
	 * @throws CustomErrorException
	 */
	private void setUpDriver() throws CustomErrorException {
		driver = DriverConfig.buildInstance(caps);
	}

	/**
	 * Method to set up the {@DesiredCapabilities}
	 * 
	 * @throws CustomErrorException
	 */
	private void setUpCapabilities() throws CustomErrorException {
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
		LOGGER.info("[ Test Configuration ] - Setting Desired Capabilities Configuration");

		caps = new DesiredCapabilities();

		/*--------------------------------------------------------------------* 
		|		GENERAL CAPABILITIES												
		*---------------------------------------------------------------------*/
		caps.setCapability("platformName",
				handler.getConfigValueFromMatrix(ConstantConfig.PLATFORM_NAME));
		caps.setCapability("appium:app", handler.getConfigValueFromMatrix(ConstantConfig.APP));

		if ((MobilePlatform.ANDROID).equalsIgnoreCase(handler.getConfigValueFromMatrix(ConstantConfig.PLATFORM_NAME))) {
			/*--------------------------------------------------------------------* 
			|		ANDROID CAPABILITIES												
			*---------------------------------------------------------------------*/
			caps.setCapability("appium:platformVersion", "8.1.0");
			caps.setCapability("appium:deviceName", "emulator_27");
			caps.setCapability("appium:noReset", false);
			caps.setCapability("appium:fullReset", false);
			caps.setCapability("appium:clearSystemFiles", true);
			caps.setCapability("appium:appPackage", "com.example.estefafdez.myfirstandroidapp");
			caps.setCapability("appium:appActivity", "");
			caps.setCapability("appium:appWaitActivity", "");
			caps.setCapability("appium:autoGrantPermissions", true);
			caps.setCapability("appium:disableAndroidWatchers", true);
			caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
			caps.setCapability("appium:unicodeKeyboard", true);

		} else if ((MobilePlatform.IOS)
				.equalsIgnoreCase(handler.getConfigValueFromMatrix(ConstantConfig.PLATFORM_NAME))) {
			/*--------------------------------------------------------------------* 
			|		IOS CAPABILITIES												
			*---------------------------------------------------------------------*/
			caps.setCapability("appium:platformVersion", "11.3");
			caps.setCapability("appium:deviceName", "iPhone 7");
			caps.setCapability("appium:noReset", false);
			caps.setCapability("appium:fullReset", false);
			caps.setCapability("appium:clearSystemFiles", true);
			caps.setCapability("appium:automationName", AutomationName.IOS_XCUI_TEST);
			caps.setCapability("appium:bundleId", "com.estefafdez.ios.test.app");
			caps.setCapability("appium:showXcodeLog", false);
			caps.setCapability("appium:showIOSLog", false);
			caps.setCapability("appium:useNewWDA", true);
			caps.setCapability("appium:resetOnSessionStartOnly", true);
			caps.setCapability("appium:autoAcceptAlerts", true);
		} else {
			throw new CustomErrorException("[ Test Configuration ] - No correct Platform selected");
		}

		checkCapabilities();
		printCapabilitiesConfig();
	}

	/**
	 * Method to check the Capabilities.
	 * 
	 * @throws CustomErrorException
	 */
	private void checkCapabilities() throws CustomErrorException {

		LOGGER.info(
				"[ Test Configuration ] - Checking if the Desired Capabilities Configuration is properly defined and does not contain nulls or empty values");

		Map<String, ?> capabilities = caps.asMap();

		for (Entry<String, ?> entry : capabilities.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue().toString();
			if (value == null) {
				throw new CustomErrorException(
						"[ Test Configuration error ] - The Desired Capability " + key + " is null");
			} else if (value.contains("$")) {
				throw new CustomErrorException(
						"[ Test Configuration error ] - The Desired Capability " + key + " is malformed to :" + value);
			} else if (value.isEmpty()) {
				LOGGER.warn("[ Test Configuration ] - The Desired Capability: " + key + " is empty");
			}
		}
	}

	/**
	 * Method to capture a screenshot when the result is false.
	 * 
	 * @param result
	 */
	private void captureScreenShots(ITestResult result) {
		String folderName;
		DateFormat df;
		try {
			folderName = "screenshot/";
			File f = driver.getScreenshotAs(OutputType.FILE);
			/** Date format fot screenshot file name */
			df = new SimpleDateFormat("yyyyMMdd_HHmm");
			/** Create dir with given folder name */
			new File(folderName).mkdir();
			/** Setting file name */
			String fileName = result.getMethod().getMethodName() + "_" + df.format(new Date()) + ".png";
			/** Copy screenshot file into screenshot folder. */
			FileUtils.copyFile(f, new File(folderName + fileName));

		} catch (IOException ex) {
			LOGGER.error("An error occurred taking a Screenshot", ex);
		}
	}

	/**
	 * Method to check if the Page is Ready
	 * 
	 * @param pageObject
	 *            to check if its ready
	 *//*
	public void isReady(BasePageObjectConfig pageObject) {

		if (pageObject == null) {
			assertTrue(false, "The Page instance is null");
		}

		assertTrue(pageObject.isReady(),
				"The Page " + pageObject.getClass().getSimpleName() + " isn't ready, it should not be displayed");
	}

	*//**
	 * Method to wait until the Page is Ready.
	 * 
	 * @param pageObject
	 *            to wait until is ready
	 *//*
	public void waitForReady(BasePageObjectConfig pageObject) {

		if (pageObject == null) {
			assertTrue(false, "The Page instance is null");
		}

		assertTrue(pageObject.waitForReady(),
				"The Page " + pageObject.getClass().getSimpleName() + " isn't ready, it should not be displayed");
	}*/

	/**
	 * Method to print all the Global Configuration on the Log.
	 */
	private static void printCapabilitiesConfig() {
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
		LOGGER.info("[ Test Configuration ] - Desired Capabilities Configuration established");
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
		if ((MobilePlatform.ANDROID).equalsIgnoreCase(handler.getConfigValueFromMatrix(ConstantConfig.PLATFORM_NAME))) {
			LOGGER.info(" DEVICE PROPERTIES");
			LOGGER.info("\tPlatform Name:\t\t" + caps.getCapability("platformName"));
			LOGGER.info("\tDevice Name:\t\t" + caps.getCapability("appium:deviceName"));
			LOGGER.info("\tPlatform Version:\t" + caps.getCapability("appium:platformVersion"));
			LOGGER.info("\tAutomation Engine:\t" + caps.getCapability("appium:automationName"));
			LOGGER.info("\tNo Reset:\t\t" + caps.getCapability("appium:noReset"));
			LOGGER.info("\tFull Reset:\t\t" + caps.getCapability("appium:fullReset"));
			LOGGER.info("\tClean System Files:\t" + caps.getCapability("appium:clearSystemFiles"));
			LOGGER.info(" APP PROPERTIES");
			LOGGER.info("\tApp:\t\t\t" + caps.getCapability("appium:app"));
			LOGGER.info(" ANDROID PROPERTIES");
			LOGGER.info("\tApp Package:\t\t" + caps.getCapability("appium:appPackage"));
			LOGGER.info("\tApp Activity:\t\t" + caps.getCapability("appium:appActivity"));
			LOGGER.info("\tApp Wait Activity:\t" + caps.getCapability("appium:appWaitActivity"));
			LOGGER.info("\tAuto Grant Permissions:\t"
					+ caps.getCapability("appium:autoGrantPermissions"));
			LOGGER.info("\tDisable Watchers:\t" + caps.getCapability("appium:disableAndroidWatchers"));
		} else {
			LOGGER.info(" DEVICE PROPERTIES");
			LOGGER.info("\tPlatform Name:\t\t" + caps.getCapability("platformName"));
			LOGGER.info("\tDevice Name:\t\t" + caps.getCapability("appium:deviceName"));
			LOGGER.info("\tPlatform Version:\t" + caps.getCapability("appium:platformVersion"));
			LOGGER.info("\tAutomation Engine:\t" + caps.getCapability("appium:automationName"));
			LOGGER.info("\tNo Reset:\t\t" + caps.getCapability("appium:noReset"));
			LOGGER.info("\tFull Reset:\t\t" + caps.getCapability("appium:fullReset"));
			LOGGER.info("\tClean System Files:\t" + caps.getCapability("appium:clearSystemFiles"));
			LOGGER.info(" APP PROPERTIES");
			LOGGER.info("\tApp:\t\t\t" + caps.getCapability("appium:app"));
			LOGGER.info(" iOS PROPERTIES");
			LOGGER.info("\tApp Package:\t\t" + caps.getCapability("appium:bundleId"));
			LOGGER.info("\tShow Xcode Log:\t\t" + caps.getCapability("appium:showXcodeLog"));
			LOGGER.info("\tShow iOS Log:\t\t" + caps.getCapability("appium:showIOSLog"));
		}
		LOGGER.info(ConstantConfig.LOG_SEPARATOR);
	}
}
