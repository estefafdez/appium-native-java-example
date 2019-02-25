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

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

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
public final class AppiumServerHandler {

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(AppiumServerHandler.class);

	/** Appium Service instance */
	private static AppiumDriverLocalService service;

	/** Appium service URL associated */
	private static String serviceUrl;
	
	public static final String LOG_SEPARATOR = "***********************************************************************************************************";
	
	/*--------------------------------------------------------------------* 
	|	CONSTRUCTOR
	*---------------------------------------------------------------------*/
	private AppiumServerHandler() {}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO HANDLER SERVER
	*---------------------------------------------------------------------*/
	
	/**
	 * Method to start the Appium Server
	 * @throws CustomErrorException custom error exception
	 */
	public static void appiumServerStart() throws Exception {
		try {
			LOGGER.info(LOG_SEPARATOR);
			LOGGER.info("[ Appium Server ] - Configuring Appium Server");

			AppiumServiceBuilder builder = new AppiumServiceBuilder();

			/** This filters ALL Appium server logs to warning level */
			builder.withArgument(GeneralServerFlag.LOG_LEVEL, "warn");
			/** Used to control the start timeouts */
			builder.withStartUpTimeOut(60, TimeUnit.SECONDS);
			/** Used to avoid port collisions */
			builder.usingAnyFreePort();

			/** Build the server */
			service = AppiumDriverLocalService.buildService(builder);

			LOGGER.info("[ Appium Server ] - Checking if previous Appium Servers are running");
			if (service.isRunning()) {
				LOGGER.info("[ Appium Server ] - Stoping previous Appium Servers to avoid problems");
				service.stop();
			}

			LOGGER.info("[ Appium Server ] - Initializing Appium Server");
			service.start();

			/** Update URL reference */
			serviceUrl = service.getUrl().toString();

			LOGGER.info("[ Appium Server ] - Server stablished on: " + serviceUrl);
			LOGGER.info(LOG_SEPARATOR);

		} catch (AppiumServerHasNotBeenStartedLocallyException ex) {
			throw new Exception("[ Appium Server error ] - The server has problems to launch", ex);
		} catch (Exception ex) {
			throw new Exception("[ Appium Server error ] - The server has problems", ex);
		}
	}

	/**
	 * Method to stop the Appium Server
	 */
	public static void appiumServerStop() {
		LOGGER.info(LOG_SEPARATOR);
		LOGGER.info("[ Appium Server ] - Stoping Appium Server");
		LOGGER.info(LOG_SEPARATOR);
		service.stop();
	}

	/**
	 * Method to get the Appium Server URL
	 * @return Appium Server URL
	 */
	public static String getAppiumServerUrl() {
		return serviceUrl;
	}
}
