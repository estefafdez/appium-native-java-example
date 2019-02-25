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
package org.estefafdez.appium.java.example.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

/**
 * <p>
 * This class is to configure all the Test Configuration.
 * </p>
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
	public static AppiumDriver<MobileElement> driver;

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(TestSetConfig.class);

	/** Create the Desired Capabilities. */
	private static DesiredCapabilities caps;
	 
	 /** Init properties**/
	 private static Properties prop = new Properties();
	 
	 /** Load properties from test.properties**/
	 private static InputStream in = DriverConfig.class.getResourceAsStream("/test.properties");    
	 
	public static final String LOG_SEPARATOR = "***********************************************************************************************************";
	
	private static String platformName = DriverConfig.getPlatformName();

	/*--------------------------------------------------------------------* 
	|		LIFE CYCLE												
	*---------------------------------------------------------------------*/

	/**
	 * This method runs before any other method.
	 */
	@BeforeSuite
	protected void setUpConfiguration() {
		LOGGER.info(LOG_SEPARATOR);
		LOGGER.info("[ Setup Configuration ] - Initializing Setup Configuration");
		try {
			setUpAppiumServer();
			setUpLevelLogger();
			setUpCapabilities();
			setUpDriver();
		} catch (Exception ex) {
			LOGGER.error("[ Setup Configuration ] - Error on Setup Configuration", ex);
		}
	}

	@BeforeMethod
	protected void beforeAllConfiguration(Method method) {
		LOGGER.info(LOG_SEPARATOR);
		LOGGER.info("[ Test Status ] - Running Test: " + method.getName());
		LOGGER.info(LOG_SEPARATOR);
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
			LOGGER.info(LOG_SEPARATOR);
			LOGGER.info("[ Test Status ] - The execution of the Test " + testName + " was FAILURE");
			this.captureScreenShots(result);
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			LOGGER.info(LOG_SEPARATOR);
			LOGGER.info("[ Test Status ] - The execution of the Test " + testName + " was SUCCESS");
		}

		LOGGER.info("[ Test Status ] - Reset the currently running App for this session");
		LOGGER.info(LOG_SEPARATOR);
		driver.resetApp();
	}

	/**
	 * Method to quit Appium. Always remember to quit.
	 */
	@AfterSuite
	protected void tearDownAppium() {
		LOGGER.info(LOG_SEPARATOR);
		LOGGER.info("[ Driver Configuration ] - Unistalling the current running App");
		driver.removeApp(getAppPackage());
		LOGGER.info("[ Driver Configuration ] - Quit this Driver, closing every instance associated");
		driver.quit();
		LOGGER.info(LOG_SEPARATOR);
		AppiumServerHandler.appiumServerStop(); 
	}
	
	/**
	 * Method to get the App Package depends on the Platform Name.
	 * @return the app package for Android or iOS
	 */
	private String getAppPackage() {
		if ((MobilePlatform.ANDROID).equalsIgnoreCase(DriverConfig.getPlatformName())) {
			return (String) caps.getCapability(AndroidMobileCapabilityType.APP_PACKAGE);
		}
		else {
			return (String) caps.getCapability(IOSMobileCapabilityType.BUNDLE_ID);
		}
	}

	/*--------------------------------------------------------------------* 
	|		SETUPS												
	*---------------------------------------------------------------------*/

	/**
	 * Method to set up the Appium Server.
	 * 
	 * @throws CustomErrorException
	 */
	private void setUpAppiumServer() throws Exception {
		AppiumServerHandler.appiumServerStart();
	}


	/**
	 * Method to set the Level Log. ALL > DEBUG > INFO > WARN > ERROR > FATAL > OFF]
	 * @throws IOException 
	 */
	private void setUpLevelLogger() throws IOException {
		Level logLevelDefault = Level.INFO;

		/** LOG Level */
		prop.load(in);
		String logLevel = prop.getProperty("logLevel");

		LOGGER.info(LOG_SEPARATOR);
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
	 * @throws Exception 
	 * 
	 * @throws CustomErrorException
	 */
	private void setUpDriver() throws Exception  {
		driver = DriverConfig.buildInstance(caps);
	}

	/**
	 * Method to set up the {@DesiredCapabilities}
	 * 
	 * @throws CustomErrorException
	 */
	private void setUpCapabilities() throws Exception {
		LOGGER.info(LOG_SEPARATOR);
		LOGGER.info("[ Test Configuration ] - Setting Desired Capabilities Configuration");
		
		platformName = DriverConfig.getPlatformName();

		caps = new DesiredCapabilities();

		if ((MobilePlatform.ANDROID).equalsIgnoreCase(platformName)) {
			/*--------------------------------------------------------------------* 
			|		ANDROID ONLY CAPABILITIES												
			*---------------------------------------------------------------------*/
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1.0");
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator_27");
			caps.setCapability(MobileCapabilityType.APP, "src/test/resources/file/AndroidBaristaApp.apk");
			caps.setCapability(MobileCapabilityType.NO_RESET, false);
			caps.setCapability(MobileCapabilityType.FULL_RESET, false);
			caps.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, true);
			caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "");
			caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.estefafdez.myfirstandroidapp");
			caps.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, "");
			caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
			caps.setCapability(AndroidMobileCapabilityType.DISABLE_ANDROID_WATCHERS, true);
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.APPIUM);

		} else if ((MobilePlatform.IOS).equalsIgnoreCase(platformName)) {
			/*--------------------------------------------------------------------* 
			|		IOS ONLY CAPABILITIES												
			*---------------------------------------------------------------------*/
			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.3");
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 7");
			caps.setCapability(MobileCapabilityType.APP, "TestApp.app");
			caps.setCapability(MobileCapabilityType.NO_RESET, false);
			caps.setCapability(MobileCapabilityType.FULL_RESET, false);
			caps.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, true);
			caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
			caps.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.estefafdez.ios.test.app");
			caps.setCapability(IOSMobileCapabilityType.SHOW_XCODE_LOG, false);
			caps.setCapability(IOSMobileCapabilityType.SHOW_IOS_LOG, false);
			caps.setCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT, 20000);
			caps.setCapability(IOSMobileCapabilityType.WDA_CONNECTION_TIMEOUT, 2000);
			caps.setCapability(IOSMobileCapabilityType.USE_NEW_WDA, true);
			caps.setCapability(IOSMobileCapabilityType.RESET_ON_SESSION_START_ONLY, true);
			caps.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);
		} else {
			throw new Exception("[ Test Configuration ] - No correct Platform selected");
		}

		printCapabilitiesConfig();
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
			folderName = "src/test/resources/screenshot";
			File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			/** Date format fot screenshot file name */
			df = new SimpleDateFormat("yyyyMMdd_HHmm");
			/** Create dir with given folder name */
			new File(folderName).mkdir();
			/** Setting file name */
			String fileName = result.getMethod().getMethodName() + "_" + df.format(new Date()) + ".png";
			/** Copy screenshot file into screenshot folder. */
			FileUtils.copyFile(f, new File(folderName + "/" + fileName));

		} catch (IOException ex) {
			LOGGER.error("An error occurred taking a Screenshot", ex);
		}
	}

	/**
	 * Method to print all the Global Configuration on the Log.
	 */
	private static void printCapabilitiesConfig() {
		LOGGER.info(LOG_SEPARATOR);
		LOGGER.info("[ Test Configuration ] - Desired Capabilities Configuration established");
		LOGGER.info(LOG_SEPARATOR);
		if ((MobilePlatform.ANDROID).equalsIgnoreCase(DriverConfig.getPlatformName())) {
			LOGGER.info(" DEVICE PROPERTIES");
			LOGGER.info("\tDevice Name:\t\t" + caps.getCapability(MobileCapabilityType.DEVICE_NAME));
			LOGGER.info("\tPlatform Name:\t\t" + caps.getCapability(MobileCapabilityType.PLATFORM_NAME));
			LOGGER.info("\tPlatform Version:\t" + caps.getCapability(MobileCapabilityType.PLATFORM_VERSION));
			LOGGER.info("\tAutomation Engine:\t" + caps.getCapability(MobileCapabilityType.AUTOMATION_NAME));
			LOGGER.info("\tNo Reset:\t\t" + caps.getCapability(MobileCapabilityType.NO_RESET));
			LOGGER.info("\tFull Reset:\t\t" + caps.getCapability(MobileCapabilityType.FULL_RESET));
			LOGGER.info("\tClean System Files:\t" + caps.getCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES));
			LOGGER.info(" APP PROPERTIES");
			LOGGER.info("\tApp:\t\t\t" + caps.getCapability(MobileCapabilityType.APP));
			LOGGER.info(" ANDROID PROPERTIES");
			LOGGER.info("\tApp Package:\t\t" + caps.getCapability(AndroidMobileCapabilityType.APP_PACKAGE));
			LOGGER.info("\tApp Activity:\t\t" + caps.getCapability(AndroidMobileCapabilityType.APP_ACTIVITY));
			LOGGER.info("\tApp Wait Activity:\t" + caps.getCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY));
			LOGGER.info("\tAuto Grant Permissions:\t"
					+ caps.getCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS));
			LOGGER.info(
					"\tDisable Watchers:\t" + caps.getCapability(AndroidMobileCapabilityType.DISABLE_ANDROID_WATCHERS));
		} else {
			LOGGER.info(" DEVICE PROPERTIES");
			LOGGER.info("\tDevice Name:\t\t" + caps.getCapability(MobileCapabilityType.DEVICE_NAME));
			LOGGER.info("\tPlatform Name:\t\t" + caps.getCapability(MobileCapabilityType.PLATFORM_NAME));
			LOGGER.info("\tPlatform Version:\t" + caps.getCapability(MobileCapabilityType.PLATFORM_VERSION));
			LOGGER.info("\tAutomation Engine:\t" + caps.getCapability(MobileCapabilityType.AUTOMATION_NAME));
			LOGGER.info("\tNo Reset:\t\t" + caps.getCapability(MobileCapabilityType.NO_RESET));
			LOGGER.info("\tFull Reset:\t\t" + caps.getCapability(MobileCapabilityType.FULL_RESET));
			LOGGER.info("\tClean System Files:\t" + caps.getCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES));
			LOGGER.info(" APP PROPERTIES");
			LOGGER.info("\tApp:\t\t\t" + caps.getCapability(MobileCapabilityType.APP));
			LOGGER.info(" iOS PROPERTIES");
			LOGGER.info("\tApp Package:\t\t" + caps.getCapability(IOSMobileCapabilityType.BUNDLE_ID));
			LOGGER.info("\tShow Xcode Log:\t\t" + caps.getCapability(IOSMobileCapabilityType.SHOW_XCODE_LOG));
			LOGGER.info("\tShow iOS Log:\t\t" + caps.getCapability(IOSMobileCapabilityType.SHOW_IOS_LOG));
			LOGGER.info("\tWDA Launch Timeout:\t\t" + caps.getCapability(IOSMobileCapabilityType.WDA_LAUNCH_TIMEOUT));
		}
		LOGGER.info(LOG_SEPARATOR);
	}
}
