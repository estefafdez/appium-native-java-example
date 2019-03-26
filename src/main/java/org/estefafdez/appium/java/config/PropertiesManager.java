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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class PropertiesManager {

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(PropertiesManager.class);

	/** Properties matrix. */
	private Map<String, Properties> propertiesMatrix;

	/** Instance of the PropertiesHandler class. */
	private static PropertiesManager instance = null;

	/** Default constructor. */
	private PropertiesManager() {
		this.propertiesMatrix = new HashMap<>();
	}

	/**
	 * Method that return an instance of the PropertiesHanler.
	 *
	 * @return PropertiesHandler instance.
	 */
	public static PropertiesManager getInstance() {
		// singleton pattern
		if (instance == null) {
			instance = new PropertiesManager();
		}
		return instance;
	}

	/**
	 * Method to load a properties file with the given filename in the properties
	 * matrix.
	 * 
	 * @param propertyFileName
	 *            Name of the properties file to read.
	 * @throws CustomErrorException error exception
	 */
	public void loadPropertiesMatrix(String propertyFileName) throws CustomErrorException {
		this.loadPropertiesMatrix(propertyFileName, null);
	}

	/**
	 * Method to load a properties file with the given filename and the file path in
	 * the properties matrix.
	 * 
	 * @param propertyFileName
	 *            Name of the properties file to read or the path to the file.
	 * @param filePath
	 *            The path to the file.
	 * @throws CustomErrorException error exception
	 */
	public void loadPropertiesMatrix(String propertyFileName, String filePath) throws CustomErrorException {

		String completeFilePath;

		// Check if is a simple path or not
		final String prop = ".properties";
		if (filePath != null && !filePath.isEmpty()) {
			// sub folder of resources
			completeFilePath = filePath + propertyFileName + prop;
		} else {
			// root resources folder
			completeFilePath = propertyFileName + prop;
		}
		

		if (!this.propertiesMatrix.containsKey(propertyFileName)) {
			this.putOnMatrix(propertyFileName, completeFilePath);
			LOGGER.info("[ Properties Configuration ] - Setting properties from: " + propertyFileName + prop);
		} else {
			LOGGER.info("[ Properties Configuration ] - The properties " + propertyFileName + " were cached, so we do not have to read anything");
		}

	}

	/**
	 * Method to return an InputStream from a file as resources.
	 * 
	 * @param filename
	 *            of the properties file to read or the path to the file.
	 * @return InputStream object.
	 */
	private InputStream getInputStream(String filePath) throws CustomErrorException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(filePath);
		if (inputStream != null) {
			return inputStream;
		} else {
			throw new CustomErrorException(
					"Could not read the properties from " + filePath + ". It seems the file does not exist");
		}
	}

	/**
	 * Method to put the property file from a file path on the matrix
	 * 
	 * @param propertyFileName name of the property file
	 * @param filePath path of the file
	 * @throws CustomErrorException error exception
	 */
	private void putOnMatrix(String propertyFileName, String filePath) throws CustomErrorException {
		Properties propertiesFile = new Properties();
		try (InputStreamReader isr = new InputStreamReader(this.getInputStream(filePath), StandardCharsets.UTF_8)) {
			propertiesFile.load(isr);
			this.propertiesMatrix.put(propertyFileName, propertiesFile);
		} catch (IOException ex) {
			throw new CustomErrorException("An error occured reading properties from " + filePath, ex);
		}
	}

	/**
	 * Method to check if a given byte array is valid UTF-8 encoded.
	 * 
	 * @param bytes number of bytes
	 * @return true when valid UTF8 encoded
	 * 
	 * Careful: Do NOT delete or comment this line. UTF-8 is mandatory. How to configure it on Eclipse:
     *           Window / Preferences / General / Content Types / Text / Java Properties File / UTF-8.
     *           Window / Preferences / General / Workspace / Text file encoding / UTF-8. 
	 */
	public static boolean isValidUTF8(final byte[] bytes) {
		LOGGER.info("Checking if the format is valid");
		try {
			Charset.availableCharsets().get("UTF-8").newDecoder().decode(ByteBuffer.wrap(bytes));

		} catch (CharacterCodingException ex) {
			LOGGER.error("[ WARNING ] - The File encoding has not been set, using platform encoding UTF-8", ex);
			return false;
		}
		return true;
	}

	/**
	 * Method to print all contain of the property.
	 * 
	 * @param propertyFilePath the path from the property file. 
	 * @throws CustomErrorException a custom error. 
	 */
	public void printThemAll(String propertyFilePath) throws CustomErrorException {
		Properties prop = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertyFilePath)) {
			if (input == null) {
				LOGGER.error("[ ERROR ] - Unable to find " + propertyFilePath);
				return;
			}
			LOGGER.info("Getting properties from: " + propertyFilePath);
			prop.load(input);

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				LOGGER.debug("Key : " + key + ", Value : " + value);
			}
		} catch (IOException ex) {
			throw new CustomErrorException("An error occured while reading the properties from " + propertyFilePath, ex);
		}
	}

	/*--------------------------------------------------------------------* 
	|	CHECKING THE VALUES FROM THE PROPERTIES
	*---------------------------------------------------------------------*/

	/**
	 * Method to check if the property name is in the property.
	 * 
	 * @param propertyName
	 *            The name of the property searched used as key to search in the
	 *            matrix
	 * @param propertyKey
	 *            Name of the key to find in the property
	 * @return true|false if the key is on the property from a property name and key. 
	 */
	public boolean isKeyOnProperty(String propertyName, String propertyKey) {
		if (this.isPropertyOnMatrix(propertyName)) {
			return this.propertiesMatrix.get(propertyName).containsKey(propertyKey);
		}
		return false;
	}

	/**
	 * Method to check if the property name is contain in the matrix keys.
	 * 
	 * @param propertyName
	 *            Name of the property used as key to search in the matrix
	 * @return true|false if the property is on the matrix. 
	 */
	public boolean isPropertyOnMatrix(String propertyName) {
		return this.propertiesMatrix.containsKey(propertyName);
	}

	/*--------------------------------------------------------------------* 
	|	GETTING THE VALUES FROM THE PROPERTIES INSIDE THE MATRIX
	*---------------------------------------------------------------------*/

	/**
	 * Get the property if this exist in the matrix.
	 * 
	 * @param propertyName
	 *            The name of the property searched using as key to search in the
	 *            matrix
	 * @return the configuration value from the properties matrix from a name.
	 */
	public Properties getPropertyFromMatrix(String propertyName) {
		if (this.isPropertyOnMatrix(propertyName)) {
			return this.propertiesMatrix.get(propertyName);
		} else {
			LOGGER.error("The property does not exist in the matrix: " + propertyName);
		}
		return null;
	}

	/**
	 * Get value of the key from the property in the matrix.
	 * 
	 * @param propertyName
	 *            The name of the property searched using as key to search in the
	 *            matrix
	 * @param propertyKey
	 *            The key in the property searched
	 * @return the configuration value from the properties matrix from a name and a key.
	 */
	public String getValueFromMatrix(String propertyName, String propertyKey) {
		if (this.isKeyOnProperty(propertyName, propertyKey)) {
			return this.propertiesMatrix.get(propertyName).getProperty(propertyKey);
		} else {
			LOGGER.error("The key [" + propertyKey + "] does not exist in the property: " + propertyName);
		}
		return null;
	}

	/**
	 * Get value of the key from the configuration file <b>test.properties</b> in
	 * the matrix.
	 * 
	 * @param propertyKey
	 *            The key in the property searched
	 * @return the configuration value from the properties matrix from a key. 
	 */
	public String getConfigValueFromMatrix(String propertyKey) {
		if (this.isKeyOnProperty(ConstantConfig.CONFIG_FILE_PROP, propertyKey)) {
			return this.propertiesMatrix.get(ConstantConfig.CONFIG_FILE_PROP).getProperty(propertyKey);
		} else {
			LOGGER.error("The key [" + propertyKey + "] does not exist in the property: " + ConstantConfig.CONFIG_FILE_PROP);
		}
		return StringUtils.EMPTY;
	}

}
