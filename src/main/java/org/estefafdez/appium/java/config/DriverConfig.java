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

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.estefafdez.appium.java.utils.AppiumServerHandler;
import org.estefafdez.appium.java.utils.CustomErrorException;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
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
public class DriverConfig {

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(DriverConfig.class);
	
	/** Handler to access to the properties matrix */
	private static PropertiesManager handler = PropertiesManager.getInstance();
	
	/** Private constructor not used. */
	private DriverConfig() {}

	/**
	 * Method to build a new Appium Driver instance with the URL and the Desired
	 * @param caps the desiredCapabilities. 
	 * @return the instance of the Driver
	 * @throws CustomErrorException custom error exception
	 */
	public static AppiumDriver<MobileElement> buildInstance(DesiredCapabilities caps) throws CustomErrorException {	
		/** Generic Driver to build the selected platform */
		AppiumDriver<MobileElement> driver = null;
		
		try {
			/** Set the server URL */
			URL serverUrl = new URL(AppiumServerHandler.getAppiumServerUrl());

			LOGGER.info("[ Driver Configuration ] - Set Up the Driver intance");
			/** Check the platform selected  */
			if((MobilePlatform.IOS).equalsIgnoreCase(handler.getConfigValueFromMatrix(ConstantConfig.PLATFORM_NAME))) {
				/** Build Android Driver */
				driver = new IOSDriver<>(serverUrl, caps);
			} else if((MobilePlatform.ANDROID).equalsIgnoreCase(handler.getConfigValueFromMatrix(ConstantConfig.PLATFORM_NAME))) {
				/** Build IOS Driver */
				driver = new AndroidDriver<>(serverUrl, caps);
			} else {
				throw new CustomErrorException(ConstantConfig.DRIVER_TYPE_NOT_CORRECT_EXCEPTION);
			}
			
			return driver;
			
		} catch (MalformedURLException ex) {
			throw new CustomErrorException("The server URL is malformed", ex);
		} catch (NullPointerException ex) {
			/** The driver instance is null cause by any capability, check this */
			throw new CustomErrorException("The driver is malformed", ex);
		} catch (Exception ex) {
			throw new CustomErrorException("Unexpected problem", ex);
		}
	}
}
