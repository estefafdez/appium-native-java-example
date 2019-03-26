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

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.estefafdez.appium.java.utils.CustomAssertHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;

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
	
	/** Get the Platform from the Test Capabilities */
	String platformName = handler.getConfigValueFromMatrix("config.platformName");

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
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO HANDLE PROPERTIES
	*---------------------------------------------------------------------*/

	/**
	 * Method to check if an element with a selector by ID is visible
	 * 
	 * @param selector element to check
	 * @return true|false if the element is visible
	 */
	protected boolean isElementVisibleByID(String selector) {
		try {
			LOGGER.info("Checking if the element is visible: [" + selector + "]");
			return this.driver.findElement(By.id(selector)).isDisplayed();
		} catch (NoSuchElementException ex) {
			LOGGER.error("The element is not visible: [" + selector + "]", ex);
			return false;
		}
	}
	
	/**
	 * Method to check if an element with a selector by Xpath is visible
	 * 
	 * @param selector element to check
	 * @return true|false if the element is visible
	 */
	protected boolean isElementVisibleByXpath(String selector) {
		try {
			LOGGER.info("Checking if the element is visible: [" + selector + "]");
			return this.driver.findElement(By.xpath(selector)).isDisplayed();
		} catch (NoSuchElementException ex) {
			LOGGER.error("The element is not visible: [" + selector + "]", ex);
			return false;
		}
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO WAIT FOR ELEMENTS
	*---------------------------------------------------------------------*/
	
	/**
	 * Method to wait until the Page is Ready with all the Page selectors as
	 * argument selected by ID.
	 * 
	 * @param selectors element to check
	 * @return true | false if the element are visible on the Page.
	 */
	protected boolean waitForReadyPageByID(List<String> selectors) {
		LOGGER.info("Waiting for the elements to be ready for: " + this.getClass().getSimpleName());
		return waitForElementsIsVisibleByID(selectors);
	}
	
	/**
	 * Method to wait to check if a list of elements with a selector are visible by ID
	 * 
	 * @param selectors element to check
	 * @return true|false if the elements are visible
	 */
	protected boolean waitForElementsIsVisibleByID(List<String> selectors) {
		for (String string : selectors) {
			if (!waitForElementIsVisibleByID(string, 10)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method to wait until the Page is Ready with all the Page selectors as
	 * argument selected by Xpath.
	 * 
	 * @param selectors element to check
	 * @return true | false if the element are visible on the Page.
	 */
	protected boolean waitForReadyPageByXpath(List<String> selectors) {
		LOGGER.info("Waiting for the elements to be ready for: " + this.getClass().getSimpleName());
		return waitForElementsIsVisibleByXpath(selectors);
	}
	
	/**
	 * Method to wait to check if a list of elements with a selector are visible by Xpath
	 * 
	 * @param selectors element to check
	 * @return true|false if the elements are visible
	 */
	protected boolean waitForElementsIsVisibleByXpath(List<String> selectors) {
		for (String string : selectors) {
			if (!waitForElementIsVisibleByXpath(string, 10)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Method to wait until an element with a selector by ID is visible
	 * 
	 * @param selector element to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is visible before the timeout is finished
	 */
	protected boolean waitForElementIsVisibleByID(String selector, long timeout) {
		try {
			LOGGER.info("Waiting for the element to be visible: [" + selector + "]");
			wait = new WebDriverWait(driver, timeout);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(selector))) != null;
		} catch (TimeoutException ex) {
			LOGGER.error("The element is not visible: [" + selector + "]", ex);
			return false;
		}
	}
	
	/**
	 * Method to wait until an element with a selector by ID is visible
	 * 
	 * @param selector element to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is visible before the timeout is finished
	 */
	protected boolean waitForElementIsVisibleByXpath(String selector, long timeout) {
		try {
			LOGGER.info("Waiting for the element to be visible: [" + selector + "]");
			wait = new WebDriverWait(driver, timeout);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selector))) != null;
		} catch (TimeoutException ex) {
			LOGGER.error("The element is not visible: [" + selector + "]", ex);
			return false;
		}
	}

	/**
	 * Method to wait until the element with a selector by ID is enabled and clickable
	 * 
	 * @param selector element to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is enabled and clickable before the timeout is finished. 
	 */
	protected boolean waitForElementIsEnabledAndClickableByID(String selector) {
		try {
			LOGGER.info("Waiting for the element to be enabled and clickable: [" + selector + "]");
			wait = new WebDriverWait(driver, 10);
			return wait.until(ExpectedConditions.elementToBeClickable(By.id(selector))) != null;
		} catch (TimeoutException ex) {
			LOGGER.error("The element is not enabled and clickable: [" + selector + "]", ex);
			return false;
		}
	}
	
	/**
	 * Method to wait until the element with a selector by Xpath is enabled and clickable
	 * 
	 * @param selector element to check
	 * @param timeout seconds to wait.
	 * @return true|false if the element is enabled and clickable before the timeout is finished. 
	 */
	protected boolean waitForElementIsEnabledAndClickableByXpath(String selector, String text, long timeout) {
		try {
			LOGGER.info("Waiting for the element to be enabled and clickable: [" + selector + "]");
			wait = new WebDriverWait(driver, 10);
			return wait.until(ExpectedConditions.elementToBeClickable(By.id(selector))) != null;
		} catch (TimeoutException ex) {
			LOGGER.error("The element is not enabled and clickable: [" + selector + "]", ex);
			return false;
		}
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO DO ACTIONS ON THE SCREEN
	*---------------------------------------------------------------------*/

	/**
	 * Method to long click on an element with a selector by ID and a duration
	 * 
	 * @param selector to perform a long click
	 * @param text of the selector
	 * @param time for the long click
	 */
	protected void performLongClickByID(String selector, int time) {
		try {
			LOGGER.info("Performing long click on the element: [" + selector + "]");

			LongPressOptions longPressOptions = new LongPressOptions();
			Duration timer = Duration.ofSeconds(time);
			action = new TouchAction<>(driver);

			if (timer.getSeconds() > 0) {
				LOGGER.info("Pressing the button for " + timer.getSeconds() + " seconds");
				longPressOptions.withDuration(timer).withElement(ElementOption.element(driver.findElement(By.id(selector))));
				action.longPress(longPressOptions).release();
				action.perform();
			} else {
				longPressOptions.withElement(ElementOption.element(driver.findElement(By.id(selector))));
				action.longPress(longPressOptions).release();
				action.perform();
			}
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError(
					"Trying to perform a long click on the element [" + selector + "] but it was not found.", ex);
		}
	}
	
	/**
	 * Method to long click on an element with a selector by ID and a duration
	 * 
	 * @param selector to perform a long click
	 * @param text of the selector
	 * @param time for the long click
	 */
	protected void performLongClickByXpath(String selector, int time) {
		try {
			LOGGER.info("Performing long click on the element: [" + selector + "]");

			LongPressOptions longPressOptions = new LongPressOptions();
			Duration timer = Duration.ofSeconds(time);
			action = new TouchAction<>(driver);

			if (timer.getSeconds() > 0) {
				LOGGER.info("Pressing the button for " + timer.getSeconds() + " seconds");
				longPressOptions.withDuration(timer).withElement(ElementOption.element(driver.findElement(By.xpath(selector))));
				action.longPress(longPressOptions).release();
				action.perform();
			} else {
				longPressOptions.withElement(ElementOption.element(driver.findElement(By.xpath(selector))));
				action.longPress(longPressOptions).release();
				action.perform();
			}
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError(
					"Trying to perform a long click on the element [" + selector + "] but it was not found.", ex);
		}
	}

	/*--------------------------------------------------------------------* 
	|	LOGIC TO DO ACTIONS ON AN ELEMENT
	*---------------------------------------------------------------------*/

	/**
	 * Method to click on an element with a selector by ID
	 * 
	 * @param selector element to find
	 */
	protected void clickOnElementByID(String selector) {
		try {
			LOGGER.info("Clicking on element: [" + selector + "]");
			this.driver.findElement(By.id(selector)).click();
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError("Trying to click on the element [" + selector + "] but it was not found.", ex);
		}
	}
	
	/**
	 * Method to click on an element with a selector by ID
	 * 
	 * @param selector element to find
	 */
	protected void clickOnElementByXpath(String selector) {
		try {
			LOGGER.info("Clicking on element: [" + selector + "]");
			this.driver.findElement(By.xpath(selector)).click();
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError("Trying to click on the element [" + selector + "] but it was not found.", ex);
		}
	}

	/**
	 * Method to send a text into an element by ID
	 * 
	 * @param selector element to find
	 * @param text to send
	 */
	protected void sendTextToElementByID(String selector, String text) {
		try {
			LOGGER.info("Sending text to element: [" + selector + "]");
			this.driver.findElement(By.id(selector)).sendKeys(text);
		} catch (NoSuchElementException ex) {
			CustomAssertHandler
					.handlerError("Is not possible to send the text into the element because this is was not found: ["
							+ selector + "]", ex);
		}
	}
	
	/**
	 * Method to send a text into an element by ID
	 * 
	 * @param selector element to find
	 * @param text to send
	 */
	protected void sendTextToElementByXpath(String selector, String text) {
		try {
			LOGGER.info("Sending text to element: [" + selector + "]");
			this.driver.findElement(By.xpath(selector)).sendKeys(text);
		} catch (NoSuchElementException ex) {
			CustomAssertHandler
					.handlerError("Is not possible to send the text into the element because this is was not found: ["
							+ selector + "]", ex);
		}
	}


	/*--------------------------------------------------------------------* 
	|	LOGIC TO GET ATTRIBUTES FOR ELEMENTS
	*---------------------------------------------------------------------*/

	/**
	 * Method to get the value of the attribute from an element using its selector by ID
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
	protected String getValueOfElementAttributeByID(String selector, String attribute) {
		String value = null;
		try {
			LOGGER.debug("Getting attribute [" + attribute + "] from element: [" + selector + "]");
			value = this.driver.findElement(By.id(selector)).getAttribute(attribute);
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError("Trying to get the attribute [" + attribute
					+ "] but the element does not contain this or the selector is missing.", ex);
		}
		return value;
	}
	
	/**
	 * Method to get the value of the attribute from an element using its selector by Xpath
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
	protected String getValueOfElementAttributeByXpath(String selector, String attribute) {
		String value = null;
		try {
			LOGGER.debug("Getting attribute [" + attribute + "] from element: [" + selector + "]");
			value = this.driver.findElement(By.xpath(selector)).getAttribute(attribute);
		} catch (NoSuchElementException ex) {
			CustomAssertHandler.handlerError("Trying to get the attribute [" + attribute
					+ "] but the element does not contain this or the selector is missing.", ex);
		}
		return value;
	}

}
