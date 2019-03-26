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
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.NoSuchContextException;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

/**
 * <p>
 * This class is to configure all the Test Configuration.
 * </p>
 * 
 * @author Francisco José Fernández González<br>
 *         <a href="mailto:ffgonzalez1989@gmail.com">ffgonzalez1989@gmail.com</a><br>
 *         <a href="https://github.com/FJFGonzalez">https://github.com/FJFGonzalez</a><br>
 *         <br>
 *         <br>
 * @author Estefanía Fernández Muñoz<br>
 *         <a href="mailto:estefafdez@gmail.com">estefafdez@gmail.com</a><br>
 *         <a href="https://github.com/estefafdez">https://github.com/estefafdez</a><br>
 *
 */
public abstract class BasePageObjectConfig {

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(BasePageObjectConfig.class);

	/** Driver instance. */
	protected final AppiumDriver<MobileElement> driver;

	/** Single instance of the PropertiesHandler */
	protected PropertiesManager handler = PropertiesManager.getInstance();

	/** Provides the ability to wait for an arbitrary condition during test execution. */
	protected WebDriverWait wait;
	
	/** Provides the event of the keys*/
	protected KeyEvent keyEvent;
	
	/** Provides the ability to using touch action during test execution */
	protected TouchAction<?> action;

	/** Provide the name of the actual page of associated property */
	protected String pagePropertyName = this.getClass().getSimpleName().replace("Page", "").toLowerCase();

	/** TIMEOUT for wait elements. (In seconds) */
	protected long waitElementTimeout = Long
			.parseLong(handler.getConfigValueFromMatrix(ConstantConfig.WAIT_ELEMENT_TIMEOUT));
	
	/** Get the Platform from the Test Capabilities */
	String platformName = handler.getConfigValueFromMatrix("config.platformName");
	
	/** Define one platformName option*/
	private static final String ANDROID = "android";
	

	/*--------------------------------------------------------------------* 
	|	CONSTRUCTOR
	*---------------------------------------------------------------------*/

	/**
	 * Construct to sets the Page's Driver. The Page structure is being used within
	 * this test in order to separate the page actions from the tests.
	 *
	 * @param driver the Appium Driver created.
	 */
	protected BasePageObjectConfig(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
		loadPageProperties();
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO HANDLE PROPERTIES
	*---------------------------------------------------------------------*/

	/**
	 * Method to build the file selector path for each Page Object.
	 */
	protected void loadPageProperties() {
		String filePath = "selectors" + File.separatorChar;
		try {
			handler.loadPropertiesMatrix(pagePropertyName, filePath);
		} catch (CustomErrorException ex) {
			CustomAssertHandler.handlerError("The load page properties fail", ex);
		}
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO CONSTRUCT THE ELEMENTS SELECTORS
	*---------------------------------------------------------------------*/

	/**
	 * Method to get the element constructor and return the element using By
	 * 
	 * @param selector selector to create the element
	 * @return elementConstructor with the selector.
	 * @throws CustomErrorException custom error exception
	 */
	private By elementConstructor(String selector) throws CustomErrorException {
		return this.elementConstructor(selector, null);
	}

	/**
	 * Method to get the element constructor along with the text inject and return
	 * the element using By
	 * 
	 * @param selector to create the element
	 * @param textInject text on the element to create
	 * @return the element to search with the correct syntax.
	 * @throws CustomErrorException custom error exception
	 */
	private By elementConstructor(String selector, String textInject) throws CustomErrorException {
		final String idType = ".id";
		final String xPathType = ".xpath";
		final char dot = '.';

		/** Value of the key to find */
		String keyValue;
		final String singleQuote = "'";

		/** Builder of the String Key to find in Properties. */
		StringBuilder keyBuild = new StringBuilder(selector);

		/** Platform Construction [Android | iOS] */
		if (platformName != null && !platformName.isEmpty()) {
			keyBuild.insert(0, platformName.toLowerCase() + dot);
		} else {
			throw new CustomErrorException("The platform is not corretly defined: " + platformName);
		}

		/** Constructor Type [ID | xPath] */
		if (handler.isKeyOnProperty(pagePropertyName, keyBuild.toString() + idType)) {
			keyBuild.append(idType);
		} else if (handler.isKeyOnProperty(pagePropertyName, keyBuild.toString() + xPathType)) {
			keyBuild.append(xPathType);
		} else {
			throw new CustomErrorException("The selector [" + selector
					+ "] is not contained or maybe is not correct in the property: " + pagePropertyName);
		}

		/** Get the value of the key */
		keyValue = handler.getValueFromMatrix(pagePropertyName, keyBuild.toString());

		/** Check the value of the key */
		if (keyValue == null || keyValue.isEmpty()) {
			throw new CustomErrorException("Trying to retrieve selector [" + keyBuild.toString()
					+ "] from properties but is missing or empty!");

		} else {
			/** Text inject constructor to inject a certain text on the element */
			if (textInject != null && !textInject.isEmpty()) {

				String textToInject;

				if (textInject.indexOf(singleQuote) > ArrayUtils.INDEX_NOT_FOUND) {
					LOGGER.debug("Contain the character ' ");
					if (textInject.indexOf('"') > -1) {
						LOGGER.debug("Contain the character ' \' ");
						textToInject = StringUtils.replace(textInject, "\"", "\"'\"");
						LOGGER.debug("Replacing with  " + textToInject);
						keyValue = keyValue.replace("$", "'" + textToInject.trim() + "'");
					} else {
						keyValue = keyValue.replace("$", '"' + textInject.trim() + '"');
					}
				} else {
					keyValue = keyValue.replace("$", "'" + textInject.trim() + "'");
				}
			}
		}

		/** FindBy Constructor to return the MobileBy with the ID or xPath */
		if (keyBuild.toString().indexOf(idType) != ArrayUtils.INDEX_NOT_FOUND) {
			return MobileBy.id(keyValue);
		} else {
			return MobileBy.xpath(keyValue);
		}

	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO CHECK ELEMENT STATE
	*---------------------------------------------------------------------*/

	/**
	 * Method to check if the page showed represents the Page Object that is
	 * implemented for.
	 * 
	 * @return boolean is the showed screen is ready to be used by this Page Object.
	 */
	public abstract boolean isReady();

	/**
	 * Method to check if the page is Ready with all the Page elements as argument
	 * 
	 * @param selectors list of elements to check
	 * @return true | false if the element are visible on the Page.
	 */
	protected boolean isReadyPage(List<String> selectors) {
		LOGGER.info("Checking for the elements to be ready for: " + this.getClass().getSimpleName());
		return isElementsVisible(selectors);
	}

	/**
	 * Method to check if an element with a @{By selector} is visible
	 * 
	 * @param selector element to check
	 * @return true|false if the element is visible
	 */
	protected boolean isElementVisible(String selector) {
		return isElementVisible(selector, null);
	}

	/**
	 * Method to check if an element with a selector is selected
	 * 
	 * @param selector element to check
	 * @return true|false if the element is selected
	 */
	protected boolean isElementSelected(String selector) {
		return isElementSelected(selector, null);
	}

	/**
	 * Method to check if an element with a selector is enabled
	 * 
	 * @param selector element to check
	 * @return true|false if the element is enabled
	 */
	protected boolean isElementEnabled(String selector) {
		return isElementEnabled(selector, null);
	}

	/**
	 * Method to check if an element with a selector is not present
	 * 
	 * @param selector element to check
	 * @return true|false if the element is not present
	 */
	protected boolean isElementNotPresent(String selector) {
		return isElementNotPresent(selector, null);
	}

	/**
	 * Method to check if an element with a selector and text is visible
	 * 
	 * @param selector element to check
	 * @param text text to check
	 * @return true|false if the element is visible
	 */
	protected boolean isElementVisible(String selector, String text) {
		try {
			LOGGER.info("Checking if the element is visible: [" + selector + "]");
			return this.driver.findElement(elementConstructor(selector, text)).isDisplayed();
		} catch (NoSuchElementException ex) {
			LOGGER.error("The element is not visible: [" + selector + "]", ex);
			return false;
		} catch (CustomErrorException ex) {
			LOGGER.error(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to check if an element with a selector and text is selected
	 * 
	 * @param selector element to check
	 * @param text text to check
	 * @return true|false if the element is selected
	 */
	protected boolean isElementSelected(String selector, String text) {
		try {
			LOGGER.info("Checking if the element is selected: [" + selector + "]");
			return this.driver.findElement(elementConstructor(selector, text)).isSelected();
		} catch (NoSuchElementException ex) {
			LOGGER.error("The element is not selected: [" + selector + "]", ex);
			return false;
		} catch (CustomErrorException ex) {
			LOGGER.error(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to check if an element with a selector and text is enabled
	 * 
	 * @param selector element to check
	 * @param text text to check
	 * @return true|false if the element is enabled
	 */
	protected boolean isElementEnabled(String selector, String text) {
		try {
			LOGGER.info("Checking if the element is enabled: [" + selector + "]");
			return this.driver.findElement(elementConstructor(selector, text)).isEnabled();
		} catch (NoSuchElementException ex) {
			LOGGER.error("The element is not enabled: [" + selector + "]", ex);
			return false;
		} catch (CustomErrorException ex) {
			LOGGER.error(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to check if an element with a selector and text is not present
	 * 
	 * @param selector element to check
	 * @param text text to check
	 * @return true|false if the element is not present
	 */
	protected boolean isElementNotPresent(String selector, String text) {
		try {
			LOGGER.info("Checking if the element is not present: [" + selector + "]");
			this.driver.findElement(elementConstructor(selector, text));
			return false;
		} catch (NoSuchElementException ex) {
			LOGGER.error("The element is present: [" + selector + "]", ex);
			return true;
		} catch (CustomErrorException ex) {
			LOGGER.error(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to check if a list of elements with a selector are visible
	 * 
	 * @param selectors element to check
	 * @return true|false if the elements are visible
	 */
	protected boolean isElementsVisible(List<String> selectors) {
		for (String string : selectors) {
			if (!isElementVisible(string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to check if the Android List is ready.
	 * 
	 * @param selector element to check
	 * @return true|false if the android list is ready
	 */
	protected boolean isAndroidListReady(String selector) {
		action = new TouchAction<>(driver);
		try {
			List<MobileElement> elements = new ArrayList<>(this.driver.findElements(elementConstructor(selector)));
			for (int i = 0; i < elements.size(); i++) {
				int init = i + 1;
				int end = i;
				if (init < elements.size()) {
					LOGGER.debug(elements.get(end) + " to " + elements.get(init));
					action.press(ElementOption.element(driver.findElement(elementConstructor(elements.get(init).toString())), 0, 0))
						.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
						.moveTo(ElementOption.element(driver.findElement(elementConstructor(elements.get(end).toString())), 0, 0))
						.release();
					action.perform();
				}
			}
			return true;
		} catch (CustomErrorException ex) {
			LOGGER.error("The list of Android component was not found", ex);
			return false;
		}
	}

	/**
	 * Method to check if the iOS List is ready.
	 * 
	 * @param list of elements to check
	 * @return true|false if the iOS list is ready
	 */
	protected boolean isIosListReady(List<String> list) {
		action = new TouchAction<>(driver);
		try {
			for (int i = 0; i < list.size(); i++) {
				int init = i + 1;
				int end = i;
				if (init < list.size()) {
					LOGGER.debug(list.get(end) + " to " + list.get(init));
					action.press(ElementOption.element(driver.findElement(elementConstructor(list.get(init))), 0, 0))
							.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
							.moveTo(ElementOption.element(driver.findElement(elementConstructor(list.get(end))), 0, 0))
							.release();
					action.perform();
	
				}
			}
			return true;
		} catch (CustomErrorException ex) {
			LOGGER.error("The list of iOS component was not found", ex);
			return false;
		}
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO WAIT FOR ELEMENTS
	*---------------------------------------------------------------------*/

	/**
	 * Method to wait until the elements are ready
	 * 
	 * @return boolean if the element is ready on the Page Object.
	 */
	public abstract boolean waitForReady();

	/**
	 * Method to wait until the Page is Ready with all the Page selectors as
	 * argument
	 * 
	 * @param selectors element to check
	 * @return true | false if the element are visible on the Page.
	 */
	protected boolean waitForReadyPage(List<String> selectors) {
		LOGGER.info("Waiting for the elements to be ready for: " + this.getClass().getSimpleName());
		return waitForElementsIsVisible(selectors);
	}
	
	/**
	 * Method to wait a number of seconds. 
	 * Important Note: PLEASE, do NOT use this method unless is a LIFE or DEATH situation.
	 * @param seconds: number of seconds to wait. 
	 */
	protected void sleep (int seconds) {
		LOGGER.info("Waiting " + seconds + " seconds. PLEASE avoid using this Method.");
		
		long numberOfMiliseconds=seconds * 1000L;
		
		try {
			Thread.sleep(numberOfMiliseconds);
		} catch (InterruptedException ex) {
			LOGGER.error("The sleep method failed.", ex);
			/** Restore interrupted state...*/
		    Thread.currentThread().interrupt();
		}
	}

	/**
	 * Method to wait until an element with a selector is present in the DOM
	 * 
	 * @param selector element to check
	 * @return true|false if the element is present
	 */
	protected boolean waitForElementIsPresent(String selector) {
		return waitForElementIsPresent(selector, null, 0);
	}

	/**
	 * Method to wait until an element with a selector is present in the DOM using
	 * timeout
	 * 
	 * @param selector element to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is present before the timeout is finished
	 */
	protected boolean waitForElementIsPresent(String selector, long timeout) {
		return waitForElementIsPresent(selector, null, timeout);
	}

	/**
	 * Method to wait until an element with a selector is present in the DOM with
	 * text
	 * 
	 * @param selector element to check
	 * @param text text to check.
	 * @return true|false if the element is present
	 */
	protected boolean waitForElementIsPresent(String selector, String text) {
		return waitForElementIsPresent(selector, text, 0);
	}

	/**
	 * Method to wait until an element with a selector is visible
	 * 
	 * @param selector element to check
	 * @return true|false if the element is visible
	 */
	protected boolean waitForElementIsVisible(String selector) {
		return waitForElementIsVisible(selector, null, 0);
	}

	/**
	 * Method to wait until an element with a selector is visible using timeout
	 * 
	 * @param selector element to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is visible before the timeout is finished
	 */
	protected boolean waitForElementIsVisible(String selector, long timeout) {
		return waitForElementIsVisible(selector, null, timeout);
	}

	/**
	 * Method to wait until an element with a selector is visible with text
	 * 
	 * @param selector element to check
	 * @param text text to check.
	 * @return true|false if the element is visible
	 */
	protected boolean waitForElementIsVisible(String selector, String text) {
		return waitForElementIsVisible(selector, text, 0);
	}

	/**
	 * Method to wait until an element with a selector and a timeout (not the default) is not visible
	 * @param selector element to check
	 * @param timeout seconds to wait. 
	 * @return true|false if the element is visible before the timeout is finished
	 */
	protected boolean waitForElementIsNotVisible(String selector, long timeout) {
		return waitForElementIsNotVisible(selector, null, timeout);
	}
	
	/**
	 * Method to wait until an element with a selector with text (and the default timeout) is not visible
	 * @param selector element to check
	 * @param text text to check
	 * @return true|false if the element is visible
	 */
	protected boolean waitForElementIsNotVisible(String selector, String text) {
		return waitForElementIsNotVisible(selector, text, 0);
	}
	
	/**
	 * Method to wait until the element with a selector is selected
	 * 
	 * @param selector element to check
	 * @return true|false if the element is selected
	 */
	protected boolean waitForElementIsSelected(String selector) {
		return waitForElementIsSelected(selector, null, 0);
	}

	/**
	 * Method to wait until the element with a selector is selected using timeout
	 * 
	 * @param selector element to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is selected before the timeout is finished
	 */
	protected boolean waitForElementIsSelected(String selector, long timeout) {
		return waitForElementIsSelected(selector, null, timeout);
	}

	/**
	 * Method to wait until the element with a selector is selected with text
	 * 
	 * @param selector element to check
	 * @param text text to check.
	 * @return true|false if the element is selected
	 */
	protected boolean waitForElementIsSelected(String selector, String text) {
		return waitForElementIsSelected(selector, text, 0);
	}

	/**
	 * Method to wait until an element with a selector is enabled and clickable
	 * 
	 * @param selector element to check
	 * @return true|false if the element is enabled and clickable
	 */
	protected boolean waitForElementIsEnabledAndClickable(String selector) {
		return waitForElementIsEnabledAndClickable(selector, null, 0);
	}

	/**
	 * Method to wait until an element with a selector is enabled and clickable
	 * using timeout
	 * 
	 * @param selector element to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is enabled and clickable before the timeout is finished
	 */
	protected boolean waitForElementIsEnabledAndClickable(String selector, long timeout) {
		return waitForElementIsEnabledAndClickable(selector, null, timeout);
	}

	/**
	 * Method to wait until an element with a selector is enabled and clickable with
	 * text
	 * 
	 * @param selector element to check
	 * @param text text to check.
	 * @return true|false if the element is enabled and clickable
	 */
	protected boolean waitForElementIsEnabledAndClickable(String selector, String text) {
		return waitForElementIsEnabledAndClickable(selector, text, 0);
	}

	/**
	 * Method to wait until an element with a selector with text is present in the
	 * DOM
	 * 
	 * @param selector element to check
	 * @param text text to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is present before the timeout is finished
	 */
	protected boolean waitForElementIsPresent(String selector, String text, long timeout) {
		try {
			LOGGER.info("Waiting for the element is present: [" + selector + "]");
			wait = new WebDriverWait(driver, 10);
			return wait.until(ExpectedConditions.presenceOfElementLocated(elementConstructor(selector, text))) != null;
		} catch (TimeoutException ex) {
			LOGGER.error("The element is not present: [" + selector + "]", ex);
			return false;
		} catch (CustomErrorException ex) {
			LOGGER.error(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to wait until an element with a selector with text is visible
	 * 
	 * @param selector element to check
	 * @param text text to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is visible before the timeout is finished
	 */
	protected boolean waitForElementIsVisible(String selector, String text, long timeout) {
		try {
			LOGGER.info("Waiting for the element to be visible: [" + selector + "]");
			wait = new WebDriverWait(driver, 10);
			return wait
					.until(ExpectedConditions.visibilityOfElementLocated(elementConstructor(selector, text))) != null;
		} catch (TimeoutException ex) {
			LOGGER.error("The element is not visible: [" + selector + "]", ex);
			return false;
		} catch (CustomErrorException ex) {
			LOGGER.error(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to wait until an element with a selector with text is not visible
	 * 
	 * @param selector element to check
	 * @param text text to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is visible before the timeout is finished
	 */
	protected boolean waitForElementIsNotVisible(String selector, String text, long timeout) {
		try {
			LOGGER.info("Waiting for the element not to be visible: [" + selector + "]");
			wait = new WebDriverWait(driver, 10);
			return wait
					.until(ExpectedConditions.invisibilityOfElementLocated(elementConstructor(selector, text))) != null;
		} catch (TimeoutException ex) {
			LOGGER.error("The element is visible: [" + selector + "]", ex);
			return false;
		} catch (CustomErrorException ex) {
			LOGGER.error(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to wait until the element with a selector with text is selected
	 * 
	 * @param selector element to check
	 * @param text text to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is selected before the timeout is finished
	 */
	protected boolean waitForElementIsSelected(String selector, String text, long timeout) {
		try {
			LOGGER.info("Waiting for the element to be selected: [" + selector + "]");
			wait = new WebDriverWait(driver, 10);
			return wait.until(ExpectedConditions.elementToBeSelected(elementConstructor(selector, text))) != null;
		} catch (TimeoutException ex) {
			LOGGER.error("The element is not selected: [" + selector + "]", ex);
			return false;
		} catch (CustomErrorException ex) {
			LOGGER.error(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to wait until the element with a selector with text is enabled and clickable
	 * 
	 * @param selector element to check
	 * @param text to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is enabled and clickable before the timeout is finished. 
	 */
	protected boolean waitForElementIsEnabledAndClickable(String selector, String text, long timeout) {
		try {
			LOGGER.info("Waiting for the element to be enabled and clickable: [" + selector + "]");
			wait = new WebDriverWait(driver, 10);
			return wait.until(ExpectedConditions.elementToBeClickable(elementConstructor(selector, text))) != null;
		} catch (TimeoutException ex) {
			LOGGER.error("The element is not enabled and clickable: [" + selector + "]", ex);
			return false;
		} catch (CustomErrorException ex) {
			LOGGER.error(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to wait to check if a list of elements with a selector are visible
	 * 
	 * @param selectors element to check
	 * @return true|false if the elements are visible
	 */
	protected boolean waitForElementsIsVisible(List<String> selectors) {
		for (String string : selectors) {
			if (!waitForElementIsVisible(string)) {
				return false;
			}
		}
		return true;
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO DO ACTIONS ON THE SCREEN
	*---------------------------------------------------------------------*/

	/**
	 * Method to perform a swipe to the left on the screen.
	 */
	protected void performSwipeLeftOnScreen() {

		int startX = (int) ((driver.manage().window().getSize().getWidth()) * 0.95);
		int endX = (int) ((driver.manage().window().getSize().getWidth()) * 0.05);
		int y = (int) ((driver.manage().window().getSize().getHeight()) * 0.50);

		LOGGER.info("Perform swipes: " + startX + " X " + y + " TO " + endX + " X " + y);

		action = new TouchAction<>(driver);

		action.press(PointOption.point(startX, y))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(endX, y))
				.release();
		action.perform();
	}

	/**
	 * Method to perform a swipe to the right on the screen.
	 */
	protected void performSwipeRightOnScreen() {

		int startX = (int) ((driver.manage().window().getSize().getWidth()) * 0.05);
		int endX = (int) ((driver.manage().window().getSize().getWidth()) * 0.95);
		int y = (int) ((driver.manage().window().getSize().getHeight()) * 0.50);

		LOGGER.info("Perform swipes: " + startX + " X " + y + " TO " + endX + " X " + y);

		action = new TouchAction<>(driver);

		action.press(PointOption.point(startX, y))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(endX, y))
				.release();
		action.perform();

	}

	/**
	 * Method to long click on an element with a selector
	 * 
	 * @param selector to perform a long click
	 */
	protected void performLongClick(String selector) {
		this.performLongClick(selector, null, 0);
	}

	/**
	 * Method to long click on an element with a selector and a text
	 * 
	 * @param selector to perform a long click
	 * @param text of the selector.
	 */
	protected void performLongClick(String selector, String text) {
		this.performLongClick(selector, text, 0);
	}

	/**
	 * Method to long click on an element with a selector and a duration
	 * 
	 * @param selector to perform a long click
	 * @param time seconds for the long click
	 */
	protected void performLongClick(String selector, int time) {
		this.performLongClick(selector, null, time);
	}

	/**
	 * Method to long click on an element with a selector, text and a duration
	 * 
	 * @param selector to perform a long click
	 * @param text of the selector
	 * @param time for the long click
	 */
	protected void performLongClick(String selector, String text, int time) {
		try {
			LOGGER.info("Performing long click on the element: [" + selector + "]");

			LongPressOptions longPressOptions = new LongPressOptions();
			Duration timer = Duration.ofSeconds(time);
			action = new TouchAction<>(driver);

			if (timer.getSeconds() > 0) {
				LOGGER.info("Pressing the button for " + timer.getSeconds() + " seconds");
				longPressOptions.withDuration(timer).withElement(ElementOption.element(driver.findElement(elementConstructor(selector, text))));
				action.longPress(longPressOptions).release();
				action.perform();
			} else {
				longPressOptions.withElement(ElementOption.element(driver.findElement(elementConstructor(selector, text))));
				action.longPress(longPressOptions).release();
				action.perform();
			}
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError(
					"Trying to perform a long click on the element [" + selector + "] but it was not found.", ex);
		} catch (CustomErrorException ex) {
			CustomAssertHandler.handlerError(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
		}
	}

	/**
	 * Method to obtain the list of mobile elements with the same selector.
	 * 
	 * @param selector to get the elements. 
	 * @return the list of mobile elements.
	 */
	protected List<MobileElement> getListOfElements(String selector) {
		List<MobileElement> elements = null;
		try {
			elements = new ArrayList<>(this.driver.findElements(elementConstructor(selector)));
		} catch (CustomErrorException ex) {
			CustomAssertHandler.handlerError(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
		}
		return elements;
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO DO ACTIONS ON AN ELEMENT
	*---------------------------------------------------------------------*/

	/**
	 * Method to click on an element with a selector
	 * 
	 * @param selector element to find
	 */
	protected void clickOnElement(String selector) {
		this.clickOnElement(selector, null);
	}

	/**
	 * Method to click on an element with a selector and a text
	 * 
	 * @param selector element to find
	 * @param text text to find
	 */
	protected void clickOnElement(String selector, String text) {

		try {
			LOGGER.info("Clicking on element: [" + selector + "]");
			this.driver.findElement(elementConstructor(selector, text)).click();
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError("Trying to click on the element [" + selector + "] but it was not found.",
					ex);
		} catch (CustomErrorException ex) {
			CustomAssertHandler.handlerError(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
		}
	}

	/**
	 * Method to send a text into an element
	 * 
	 * @param selector element to find
	 * @param text to send
	 */
	protected void sendTextToElement(String selector, String text) {
		try {
			LOGGER.info("Sending text to element: [" + selector + "]");
			this.driver.findElement(elementConstructor(selector, text)).sendKeys(text);
		} catch (NoSuchElementException ex) {
			CustomAssertHandler
					.handlerError("Is not possible to send the text into the element because this is was not found: ["
							+ selector + "]", ex);
		} catch (CustomErrorException ex) {
			CustomAssertHandler.handlerError(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
		}
	}

	/**
	 * Method to send letter by letter a text into an element.
	 * Important Note: send special characters (in Unicode) is not available with this method. 
	 * Use sendTextToElement instead
	 * 
	 * @param selector element to find
	 * @param text text to send
	 */
	protected void sendTextCharByCharToElement(String selector, String text) {
		try {
			LOGGER.info("Sending text char by char to element: [" + selector + "]");
			if (text != null) {
				for (char c : text.toCharArray()) {
					if (ANDROID.equalsIgnoreCase(platformName)) {
						/** The setValue method do not trigger the auto-complete on the Android Keyboard. */
						this.driver.findElement(elementConstructor(selector)).setValue(Character.toString(c));
					}
					else {
						this.driver.findElement(elementConstructor(selector)).sendKeys(Character.toString(c));
					}
					LOGGER.debug(Character.toString(c));
				}
			}
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError(
					"Is not possible to send the text letter by letter to the element because this was not found: ["
							+ selector + "]",
					ex);
		} catch (CustomErrorException ex) {
			CustomAssertHandler.handlerError(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
		}
	}
	
	/**
	 * Method to clear an element
	 * 
	 * @param selector element to find
	 */
	protected void clearTextFromElement(String selector) {
		try {
			LOGGER.info("Clearing text from element: [" + selector + "]");
			this.driver.findElement(elementConstructor(selector)).clear();
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError(
					"Is not possible to clear the text from the element because this was not found: [" + selector + "]",
					ex);
		} catch (CustomErrorException ex) {
			CustomAssertHandler.handlerError(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
		}
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO GET ATTRIBUTES FOR ELEMENTS
	*---------------------------------------------------------------------*/

	/**
	 * Method to get the value of the attribute from an element using its selector.
	 * 
	 * <pre>
	 * <b>Attributes for Android</b>
	 * - index
	 * - text
	 * - class
	 * - package
	 * - content-desc
	 * - checkable
	 * - checked
	 * - clickable
	 * - enabled
	 * - focusable
	 * - focused
	 * - scrollable
	 * - long-clickable 
	 * - password
	 * - selected
	 * - bounds
	 * - resource-id
	 * - instance
	 * </pre>
	 * 
	 * @param selector element to get attribute
	 * @param attribute name of the attribute to find
	 * @return the value of the attribute
	 */
	protected String getValueOfElementAttribute(String selector, String attribute) {
		String value = null;
		try {
			LOGGER.debug("Getting attribute [" + attribute + "] from element: [" + selector + "]");
			value = this.driver.findElement(elementConstructor(selector)).getAttribute(attribute);
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError("Trying to get the attribute [" + attribute
					+ "] but the element does not contain this or the selector is missing.", ex);
		} catch (CustomErrorException ex) {
			CustomAssertHandler.handlerError(ConstantConfig.SELECTOR_CONTRUCTOR_ERROR + " [" + selector + "]", ex);
		}
		return value;
	}

	/**
	 * Get the source of the last loaded page. If the page has been modified after
	 * loading (for example, by Javascript) there is no guarantee that the returned
	 * text is that of the modified page. The page source returned is a
	 * representation of the underlying DOM.
	 * 
	 * @return The source of the current page.
	 */
	protected String getPageDOM() {
		LOGGER.info("Getting DOM from: " + this.getClass().getSimpleName());
		return this.driver.getPageSource();
	}
	
	/**
	 * Method to get the current context in which Appium is running
	 * @return the context: it could be: NATIVE_APP or WEBVIEW.
	 */
	protected String getContext() {
		LOGGER.info("Getting Context from: " + this.getClass().getSimpleName());
		return this.driver.getContext();
	}
	
	/**
	 * Method to switch from current context in which Appium is running to another.
	 * We can switch from NATIVE_APP to WEBVIEW or the other way around.
	 */
	protected void switchContext(String context) {
		LOGGER.info("Switching context to: " + context + " in " + this.getClass().getSimpleName());
		if (context != null) {
			try {
				if (context.equalsIgnoreCase("NATIVE_APP") || context.equalsIgnoreCase("WEBVIEW")) {
					this.driver.context(context);
				}
				else {
					CustomAssertHandler.handlerError("The context is not correct. You need to choose"
							+ " between NATIVE_APP or WEBVIEW");
				}
			}
			catch (NoSuchContextException ex) {
					CustomAssertHandler.handlerError("Trying to switch to context: [" + context 
					+ "] but it is not possible", ex);
			}
		}
	}

	/**
	 * Method to check if an element with a selector is on a determinate list
	 * 
	 * @param selector element to find
	 * @return true|false if the element is on the list.
	 * @throws CustomErrorException custom error exception
	 */
	protected boolean isElementOnIndeterminateList(String selector) throws CustomErrorException {
		return isElementOnIndeterminateList(selector, null, 1, 5, 0);
	}

	/**
	 * Method to check if an element with a selector and a text is on a determinate
	 * list.
	 * 
	 * @param selector element to find
	 * @param text text to find
	 * @return true|false if the element is on the list.
	 * @throws CustomErrorException custom error exception
	 */
	protected boolean isElementOnIndeterminateList(String selector, String text) throws CustomErrorException {
		return isElementOnIndeterminateList(selector, text, 1, 5, 0);
	}

	/**
	 * Method to check if an element with a selector is on a determinate list with a
	 * custom number of attempt.
	 * 
	 * @param selector element to find
	 * @param retrying number of retrying
	 * @return true|false if the element is on the list.
	 * @throws CustomErrorException custom error exception
	 */
	protected boolean isElementOnIndeterminateList(String selector, int retrying) throws CustomErrorException {
		return isElementOnIndeterminateList(selector, null, 1, retrying, 0);
	}

	/**
	 * Method to check if an element with a selector and a text is on a determinate
	 * list with a custom number of attempt.
	 * 
	 * @param selector element to find
	 * @param text text to find
	 * @param retrying number of retrying
	 * @return true|false if the element is on the list.
	 * @throws CustomErrorException custom error exception
	 */
	protected boolean isElementOnIndeterminateList(String selector, String text, int retrying)
			throws CustomErrorException {
		return isElementOnIndeterminateList(selector, text, 1, retrying, 0);
	}

	/**
	 * Method to check if an element with a selector and a text is on a determinate
	 * list with a certain number of attempt.
	 * 
	 * @param selector element to find
	 * @param text text to find
	 * @param counter number of attempt
	 * @param timeout seconds to wait.
	 * @return true|false if the element is on the indeterminate list
	 * @throws CustomErrorException custom error exception
	 */
	private boolean isElementOnIndeterminateList(String selector, String text, int counter, int retrying, long timeout)
			throws CustomErrorException {
		boolean flag = false;
		int retryingCounter = 1;
		try {
			if (retrying > 0) {
				retryingCounter = retrying;
			}
			wait = new WebDriverWait(this.driver, 10);
			LOGGER.info("Checking " + elementConstructor(selector, text).toString());
			flag = this.driver.findElement(elementConstructor(selector, text)).isDisplayed();
		} catch (NoSuchElementException nse) {
			if (counter <= retryingCounter) {
				LOGGER.info("Performing scroll to find the element | Retrying " + counter + " to " + retryingCounter);
				performIndeterminateScroll();
				int count = counter;
				isElementOnIndeterminateList(selector, text, ++count, retryingCounter, 10);
			} else {
				throw new CustomErrorException(ConstantConfig.EXCEEDED_NUMBER_ATTEMPTS_FIND_ELEMENT, nse);
			}
		}
		return flag;
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO SCROLL
	*---------------------------------------------------------------------*/

	/**
	 * Method to perform an indeterminate scroll in the screen.
	 */
	protected void performIndeterminateScroll() {
		action = new TouchAction<>(driver);

		int x = (int) ((driver.manage().window().getSize().getWidth()) * 0.50);
		int startY = (int) ((driver.manage().window().getSize().getHeight()) * 0.65);
		int endY = (int) ((driver.manage().window().getSize().getHeight()) * 0.35);

		LOGGER.info("Performing indeterminate scroll: " + x + " X " + startY + " TO " + x + " X " + endY);
		
		action.press(PointOption.point(x, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
				.moveTo(PointOption.point(x, endY))
				.release();
		action.perform();
	}
	
	/*--------------------------------------------------------------------* 
	|	ANDROID AND IOS METHODS
	*---------------------------------------------------------------------*/

	/**
	 * Method to hide the Keyboard (For Android and iOS)
	 */
	protected void hideKeyboard() {
		LOGGER.info("Hiding Keyboard");
		driver.hideKeyboard();	
	}

	/*--------------------------------------------------------------------* 
	|	ONLY ANDROID METHODS
	*---------------------------------------------------------------------*/
	
	/**
	 * Method to click on the native back button (Only for Android)
	 */
	protected void clickOnBackButtonAndroid() {
		LOGGER.info("Clicking on back physical button");
		keyEvent = new KeyEvent(AndroidKey.BACK);	
		((AndroidDriver<MobileElement>) driver).pressKey(keyEvent);
	}
	
	/**
	 * Method to click on the native enter button on the Keyboard (Only for Android)
	 */
	protected void clickOnEnterButtonAndroid() {
		LOGGER.info("Clicking on enter keyboard button");
		keyEvent = new KeyEvent(AndroidKey.ENTER);
		((AndroidDriver<MobileElement>) driver).pressKey(keyEvent);
	}
	
	/**
	 * Method to click on the native magnifier button on the Keyboard (Only for Android)
	 */
	protected void clickOnKeyboardMagnifierButtonAndroid() {
		LOGGER.info("Clicking on the Magnifier keyboard button");
		driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
	}

	/**
	 * Method to click on the native home button (Only for Android)
	 */
	protected void clickOnHomeButtonAndroid() {
		LOGGER.info("Clicking on home physical button");
		keyEvent = new KeyEvent(AndroidKey.HOME);	
		((AndroidDriver<MobileElement>) driver).pressKey(keyEvent);
	}

	/**
	 * Method to open the notifications menu (Only for Android)
	 */
	protected void openNotificationsAndroid() {
		LOGGER.info("Open Android notification.");
		((AndroidDriver<MobileElement>) driver).openNotifications();
	}

	/*--------------------------------------------------------------------* 
	|	ONLY iOS METHODS
	*---------------------------------------------------------------------*/

	/**
	 * Method to shake the device (Only for iOS)
	 */
	protected void screenShakeIos() {
		LOGGER.info("Shaking iOS Screen.");
		((IOSDriver<MobileElement>) driver).shake();
	}

}
