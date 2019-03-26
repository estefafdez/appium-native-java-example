package org.estefafdez.appium.java.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.estefafdez.appium.java.config.BasePageObjectConfig;
import org.estefafdez.appium.java.constant.AndroidHomeConst;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AndroidHomePage extends BasePageObjectConfig{

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(AndroidHomePage.class);
	
	/**
	 * Class constructor.
	 * @param driver
	 */
	public AndroidHomePage(AppiumDriver<MobileElement> driver) {
		super(driver);
	}

	
	/**
	 * Method to wait until the Page is Ready. 
	 */
	public boolean waitForReadyPageByID() {
		LOGGER.info("Launch waitForReady " + this.getClass().getSimpleName());
		return this.waitForReadyPageByID(AndroidHomeConst.SET_UP_ANDROID_IS_READY);
	}
	
	/**
	 * Method to click on the input (edit text)
	 */
	public void clickOnInput() {
		this.clickOnElementByID(AndroidHomeConst.BODY_INPUT_TEXTBOX);
	}
	
	/**
	 * Method to perform a long click on the input (edit text)
	 */
	public void performLongClickOnInput() {
		this.performLongClickByID(AndroidHomeConst.BODY_INPUT_TEXTBOX, 4);
	}
	
	/**
	 * Method to send a text into the input (edit text).
	 * @param text: text to send on the element. 
	 */
	public void sendKeyToInput(String text) {
		this.sendTextToElementByID(AndroidHomeConst.BODY_INPUT_TEXTBOX, text);
	}
	
	/**
	 * Method to check if the button 1 is visible.
	 */
	public boolean checkButton1IsVisible() {
		return this.isElementVisibleByID(AndroidHomeConst.BODY_BUTTON_BUTTON1);
	}
	
	/**
	 * Method to click on the button 1
	 */
	public void clickOnButton1() {
		this.clickOnElementByID(AndroidHomeConst.BODY_BUTTON_BUTTON1);
	}
	
	/**
	 * Method to check if the button 2 is Enable and Clickable.
	 */
	public boolean waitForButton2IsEnableAndClickable() {
		return this.waitForElementIsEnabledAndClickableByID(AndroidHomeConst.BODY_BUTTON_BUTTON1);
	}
	
	/**
	 * Method to click on the button 2
	 */
	public void clickOnButton2() {
		this.clickOnElementByID(AndroidHomeConst.BODY_BUTTON_BUTTON2);
	}
	
	/**
	 * Method to click on the button 3
	 */
	public void clickOnButton3() {
		this.clickOnElementByID(AndroidHomeConst.BODY_BUTTON_BUTTON3);
	}
	
	/**
	 * Method to click on the button "Click here"
	 */
	public void clickOnButton4() {
		this.clickOnElementByID(AndroidHomeConst.BODY_BUTTON_BUTTON4);
	}
}
