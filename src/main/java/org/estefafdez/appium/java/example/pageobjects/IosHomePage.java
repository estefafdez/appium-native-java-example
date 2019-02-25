package org.estefafdez.appium.java.example.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.estefafdez.appium.java.example.config.BasePageObject;
import org.estefafdez.appium.java.example.constant.IosHomeConst;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class IosHomePage extends BasePageObject{

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(IosHomePage.class);
	
	/** Provides the ability to using touch action during test execution */
	protected TouchAction<?> action;
	
	/** Provides the ability to wait for an arbitrary condition during test execution. */
	protected WebDriverWait wait;
	
	/**
	 * Class constructor
	 * @param driver
	 */
	public IosHomePage(AppiumDriver<MobileElement> driver) {
		super(driver);
	}

	/**
	 * Method wait for ready
	 */
	public void waitForReady() {
		LOGGER.info("Launch waitForReady " + this.getClass().getSimpleName());
		
		 List<String> iOSHomeElements = new ArrayList<String>();
		 iOSHomeElements.add(IosHomeConst.BODY_LABEL_ENTER_NAME_ID);
		 iOSHomeElements.add(IosHomeConst.BODY_INPUT_ENTER_YOUR_NAME_ID);
		 iOSHomeElements.add(IosHomeConst.BODY_BUTTON_CLICK_ID);
		 iOSHomeElements.add(IosHomeConst.BODY_LABEL_SWITCH_BUTTON_TEXT_ID);
		 iOSHomeElements.add(IosHomeConst.BODY_BUTTON_SWITCH_ID);
		 iOSHomeElements.add(IosHomeConst.BODY_LABEL_HEY_THERE_ID);
		 iOSHomeElements.add(IosHomeConst.BODY_BUTTON_INCREASE_TEXT_ID);
		 
		 for (int i = 0; i <= iOSHomeElements.size() - 1; i++) {
			LOGGER.info("Waiting for the element to be visible: [" + iOSHomeElements.get(i) + "]");
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(iOSHomeElements.get(i))));
       }
	}
	
	/**
	 * Method to click on the button Click!
	 */
	public void clickOnButton() {
		LOGGER.info("Clicking on the element: [" + IosHomeConst.BODY_BUTTON_CLICK_ID + "]");
		driver.findElement(By.id(IosHomeConst.BODY_BUTTON_CLICK_ID)).click();
	}
	
	/**
	 * Method to send the text into the input
	 * @param text to send
	 */
	public void sendKeyToInput(String text) {
		LOGGER.info("Sending the text: " + text +" to the element: [" + IosHomeConst.BODY_INPUT_ENTER_YOUR_NAME_ID + "]");
		driver.findElement(By.id(IosHomeConst.BODY_INPUT_ENTER_YOUR_NAME_ID)).sendKeys(text);
	}

	
	/**
	 * Method to check the label on the switch button label
	 * @param text to check on the label
	 */
	public boolean checkSwitchLabel(String text) {
		LOGGER.info("Checking if the element is visible: [" + IosHomeConst.BODY_LABEL_SWITCH_BUTTON_TEXT_ID + "]");
		return driver.findElement(By.id(IosHomeConst.BODY_LABEL_SWITCH_BUTTON_TEXT_ID)).isDisplayed();
	}
	
	/**
	 * Method to check the status of the switch button. 
	 * We need to check the value of the element. 1: enable, 0: disable.
	 * @return true|false with the value of the status. 1 true, 0 false. 
	 */
	public boolean checkSwitchButtonStatus() {
		LOGGER.debug("Getting attribute [" + "value" + "] from element: [" + IosHomeConst.BODY_BUTTON_SWITCH_ID + "]");
		return this.driver.findElement(By.id(IosHomeConst.BODY_BUTTON_SWITCH_ID)).getAttribute("value").equalsIgnoreCase("1");
	}
	
	/**
	 * Method to click on the switch button.
	 */
	public void clickOnSwitchButton() {
		LOGGER.info("Clicking on the element: [" + IosHomeConst.BODY_BUTTON_SWITCH_ID + "]");
		driver.findElement(By.id(IosHomeConst.BODY_BUTTON_SWITCH_ID)).click();
	}

	/**
	 * Method to click on the input text box. 
	 */
	public void clickOnInput() {
		LOGGER.info("Clicking on the element: [" + IosHomeConst.BODY_INPUT_ENTER_YOUR_NAME_ID + "]");
		driver.findElement(By.id(IosHomeConst.BODY_INPUT_ENTER_YOUR_NAME_ID)).click();
	}

}
