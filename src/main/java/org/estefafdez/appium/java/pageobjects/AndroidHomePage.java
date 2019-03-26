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
	 * Method to check if the Page is Ready.
	 */
	@Override
	public boolean isReady() {
		LOGGER.info("Launch isReady " + this.getClass().getSimpleName());
		return this.isReadyPage(AndroidHomeConst.SET_UP_ANDROID_IS_READY);
	}

	/**
	 * Method to wait until the Page is Ready. 
	 */
	@Override
	public boolean waitForReady() {
		LOGGER.info("Launch waitForReady " + this.getClass().getSimpleName());
		return this.waitForReadyPage(AndroidHomeConst.SET_UP_ANDROID_IS_READY);
	}
	
	/**
	 * Method to click on the input (edit text)
	 */
	public void clickOnInput() {
		this.clickOnElement(AndroidHomeConst.BODY_INPUT_TEXTBOX);
	}
	
	/**
	 * Method to perform a long click on the input (edit text)
	 */
	public void performLongClickOnInput() {
		this.performLongClick(AndroidHomeConst.BODY_INPUT_TEXTBOX);
	}
	
	/**
	 * Method to send a text into the input (edit text).
	 * @param text: text to send on the element. 
	 */
	public void sendKeyToInput(String text) {
		this.sendTextToElement(AndroidHomeConst.BODY_INPUT_TEXTBOX, text);
	}
	
	/**
	 * Method to check if the button 1 is visible.
	 */
	public boolean checkButton1IsVisible() {
		return this.isElementVisible(AndroidHomeConst.BODY_BUTTON_BUTTON1);
	}
	
	/**
	 * Method to click on the button 1
	 */
	public void clickOnButton1() {
		this.clickOnElement(AndroidHomeConst.BODY_BUTTON_BUTTON1);
	}
	
	/**
	 * Method to check if the button 2 is Enable and Clickable.
	 */
	public boolean waitForButton2IsEnableAndClickable() {
		return this.waitForElementIsEnabledAndClickable(AndroidHomeConst.BODY_BUTTON_BUTTON1);
	}
	
	/**
	 * Method to click on the button 2
	 */
	public void clickOnButton2() {
		this.clickOnElement(AndroidHomeConst.BODY_BUTTON_BUTTON2);
	}
	
	/**
	 * Method to click on the button 3
	 */
	public void clickOnButton3() {
		this.clickOnElement(AndroidHomeConst.BODY_BUTTON_BUTTON3);
	}
	
	/**
	 * Method to click on the button "Click here"
	 */
	public void clickOnButton4() {
		this.clickOnElement(AndroidHomeConst.BODY_BUTTON_BUTTON4);
	}
	
	/**
	 * Method get the context from the page.
	 */
	public void getHomePageContext() {
		String context = this.getContext();
		LOGGER.info("The context is: " + context);
		this.switchContext("aaa");
	}
	
}
