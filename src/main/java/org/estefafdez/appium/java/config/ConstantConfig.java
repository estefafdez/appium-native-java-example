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

import java.util.Properties;

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
public final class ConstantConfig {
	
	/**
	 * Key to be used in the {@link PropertiesManager} to get the value of 
	 * the pom.xml
	 * <p>
	 * This constant is the name of the properties file that associates the
	 * {@link Properties} keys with the values ​​of the pom.xml
	 * 
	 * <pre>
	 * Example of value: src/test/resources/test.properties
	 * </pre>
	 */
	public static final String CONFIG_FILE_PROP = "test";
	
	/**
	 * Key to be used in the {@link PropertiesManager} to get the value of 
	 * the pom.xml.
	 * <p>
	 * This constant is the O.S. of the device used to emulated.
	 * 
	 * <pre>
	 * Example of value: android
	 * </pre>
	 */
	public static final String PLATFORM_NAME = "config.platformName";

	/**
	 * Key to be used in the {@link PropertiesManager} to get the value of 
	 * the pom.xml.
	 * <p>
	 * This constant is the name and the path of the APP used to emulated.
	 * 
	 * <pre>
	 * Example of value: mi_app.apk | iosApp.app
	 * </pre>
	 */
	public static final String APP = "config.app";
	
	/*--------------------------------------------------------------------* 
	|							CUSTOM ERRORS
	*---------------------------------------------------------------------*/
	
	/**
	 * Error to be launch if the element can't be found. 
	 */
	public static final String NO_SUCH_ELEMENT_EXCEPTION = "Method can’t find the element.";
	
	/**
	 * Error to be launch if the element is not present on the DOM page.
	 */
	public static final String STALE_ELEMENT_REFERENCE_EXCEPTION = "The element is no longer appearing on the DOM page.";
	
	/**
	 * Error to be launch if the execution failed because the command is not complete in enough time.
	 */
	public static final String TIMEOUT_EXCEPTION = "The execution is failed because the command did not complete in enough time.";
	
	/**
	 * Error to be launch if the element is present on the DOM but it's not visible and it's not possible to interact with it. 
	 */
	public static final String ELEMENT_NOT_VISIBLE_EXCEPTION = "The element is present on the DOM, but it is not visible, and it is not possible to interact with it.";
	
	/**
	 * Error to be launch if the element is disabled and it's not possible to select it. 
	 */
	public static final String ELEMENT_NOT_SELECTEABLE_EXCEPTION = "The element is disabled, and so is not able to select.";

	/**
	 * Error to be launch if the Driver type is not correct.
	 */
	public static final String DRIVER_TYPE_NOT_CORRECT_EXCEPTION = "The Driver Type is not the correct or maybe it is not supported";
	
	/**
	 * Error to be launch when a generic error is found. 
	 */
	public static final String GENERIC_EXCEPTION = "Generic exception error";
	
	/**
	 * Error to be launch when a generic error is found. 
	 */
	public static final String EXCEEDED_NUMBER_ATTEMPTS_FIND_ELEMENT = "Exceeded number of attempts to find the element";
	
	/**
	 * Error to be launch when a generic error is found. 
	 */
	public static final String SELECTOR_CONTRUCTOR_ERROR = "The selector have errors and don't constructed correctly.";
	
	/*--------------------------------------------------------------------* 
	|							LOG LEVEL
	*---------------------------------------------------------------------*/
  	
	/**
	 * Key to be used in the {@link PropertiesManager} to get the value of 
	 * the pom.xml.
	 * <p>
	 * This constant is the LOG level you want to have on the project.
	 * This value <b> is not mandatory </b>
	 * 
	 * <pre>
	 * Example of value from more to less visibility: ALL - DEBUG - INFO - WARN - ERROR - FATAL - OFF
	 * </pre>
     * 
     */
	public static final String LOG_LEVEL = "config.logLevel";
	
	/**
	 * Key to be used in the {@link PropertiesManager} to get the value of 
	 * the pom.xml.
	 * <p>
	 * This constant is the Appium Log level you want to have on the project.
	 * This value <b> is not mandatory </b>
	 * 
	 * <pre>
	 * Example of value from more to less visibility: DEBUG - INFO - WARN
	 * </pre>
     * 
     */
	
	public static final String APPIUM_LOG_LEVEL = "config.appiumServerLogLevel";
	
	/**
	 * Key to be used in the {@link PropertiesManager} to get the value of 
	 * the pom.xml.
	 * <p>
	 * This constant define the timeout for the wait element actions
	 * This value <b> is not mandatory </b>
	 * 
	 * <pre>
	 * Example of value: 10
	 * </pre>
     * 
     */
	public static final String WAIT_ELEMENT_TIMEOUT="config.waitElementTimeout";
	
	/*--------------------------------------------------------------------* 
	|							SERVER LEVEL LOG
	*---------------------------------------------------------------------*/
	
	public static final String SERV_LOG_LEVEL_WARN = "warn";
	public static final String SERV_LOG_LEVEL_INFO = "info";
	public static final String SERV_LOG_LEVEL_DEBUG = "debug";
	
	
	/*--------------------------------------------------------------------* 
	|							PRIVATE CONSTRUCTOR
	*---------------------------------------------------------------------*/
	
	public static final String LOG_SEPARATOR = "***********************************************************************************************************";
	
	/**
	 * Private constructor. 
	 */
	 private ConstantConfig() {} 
	
}