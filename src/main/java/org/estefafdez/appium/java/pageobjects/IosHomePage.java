package org.estefafdez.appium.java.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.estefafdez.appium.java.config.BasePageObjectConfig;
import org.estefafdez.appium.java.constant.IosHomeConst;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class IosHomePage extends BasePageObjectConfig{

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(IosHomePage.class);
	
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
	public boolean waitForReadyPageByID() {
		LOGGER.info("Launch waitForReady " + this.getClass().getSimpleName());
		return this.waitForReadyPageByID(IosHomeConst.SET_UP_IOS_IS_READY);
	}
	
	/**
	 * Method to click on the button Click!
	 */
	public void clickOnButton() {
		this.clickOnElementByID(IosHomeConst.BODY_BUTTON_CLICK);
	}
	
	/**
	 * Method to send the text into the input
	 * @param text to send
	 */
	public void sendKeyToInput(String text) {
		this.sendTextToElementByID(IosHomeConst.BODY_INPUT_ENTER_YOUR_NAME, text);
	}

	
	/**
	 * Method to check the label on the switch button label
	 * @param text to check on the label
	 */
	public boolean checkSwitchLabel(String text) {
		return this.getValueOfElementAttributeByID(IosHomeConst.BODY_LABEL_SWITCH_BUTTON_TEXT, "value").equals(text);
	}
	
	/**
	 * Method to check the status of the switch button. 
	 * We need to check the value of the element. 1: enable, 0: disable.
	 * @return true|false with the value of the status. 1 true, 0 false. 
	 */
	public boolean checkSwitchButtonStatus() {
		return this.getValueOfElementAttributeByID(IosHomeConst.BODY_BUTTON_SWITCH, "value").equalsIgnoreCase("1");
	}
	
	/**
	 * Method to click on the switch button.
	 */
	public void clickOnSwitchButton() {
		this.clickOnElementByID(IosHomeConst.BODY_BUTTON_SWITCH);
	}

	/**
	 * Method to click on the input text box. 
	 */
	public void clickOnInput() {
		this.clickOnElementByID(IosHomeConst.BODY_INPUT_ENTER_YOUR_NAME);
	}
	
}
