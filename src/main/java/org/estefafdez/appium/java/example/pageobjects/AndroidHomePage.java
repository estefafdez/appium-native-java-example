package org.estefafdez.appium.java.example.pageobjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.estefafdez.appium.java.example.config.BasePageObject;
import org.estefafdez.appium.java.example.constant.AndroidHomeConst;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class AndroidHomePage extends BasePageObject {

	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(AndroidHomePage.class);
	
	/** Provides the ability to using touch action during test execution */
	protected TouchAction<?> action;
	
	/** Provides the ability to wait for an arbitrary condition during test execution. */
	protected WebDriverWait wait;
	
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
	public void waitForReady() {
		LOGGER.info("Launch waitForReady " + this.getClass().getSimpleName());
		
		 List<String> androidHomeElements = new ArrayList<String>();
		 androidHomeElements.add(AndroidHomeConst.HEADER_LABEL_TITLE_ID);
		 androidHomeElements.add(AndroidHomeConst.BODY_LAYOUT_ID);
		 androidHomeElements.add(AndroidHomeConst.BODY_LABEL_SUBTITLE_ID);
		 androidHomeElements.add(AndroidHomeConst.BODY_INPUT_TEXTBOX_ID);
		 androidHomeElements.add(AndroidHomeConst.BODY_BUTTON_BUTTON1_ID);
		 androidHomeElements.add(AndroidHomeConst.BODY_BUTTON_BUTTON2_ID);
		 androidHomeElements.add(AndroidHomeConst.BODY_BUTTON_BUTTON3_ID);
		 androidHomeElements.add(AndroidHomeConst.BODY_BUTTON_BUTTON4_ID);

		 for (int i = 0; i <= androidHomeElements.size() - 1; i++) {
			LOGGER.info("Waiting for the element to be visible: [" + androidHomeElements.get(i) + "]");
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(androidHomeElements.get(i))));
        }
	}
	
	/**
	 * Method to click on the input (edit text)
	 */
	public void clickOnInput() {
		LOGGER.info("Clicking on the element: [" + AndroidHomeConst.BODY_INPUT_TEXTBOX_ID + "]");
		driver.findElement(By.id(AndroidHomeConst.BODY_INPUT_TEXTBOX_ID)).click();
	}
	
	/**
	 * Method to perform a long click on the input (edit text)
	 */
	public void performLongClickOnInput(int time) {
		LOGGER.info("Performing long click on the element: [" + AndroidHomeConst.BODY_INPUT_TEXTBOX_ID + "]");
		LongPressOptions longPressOptions = new LongPressOptions();
		Duration timer = Duration.ofSeconds(time);
		action = new TouchAction<>(driver);
			
		LOGGER.info("Pressing the button for " + timer.getSeconds() + " seconds");
		longPressOptions.withDuration(timer).withElement(ElementOption.element(driver.findElement(By.id(AndroidHomeConst.BODY_INPUT_TEXTBOX_ID))));
		action.longPress(longPressOptions).release();
		action.perform();
	}
	
	/**
	 * Method to send a text into the input (edit text).
	 * @param text: text to send on the element. 
	 */
	public void sendKeyToInput(String text) {
		LOGGER.info("Sending the text: " + text +" to the element: [" + AndroidHomeConst.BODY_INPUT_TEXTBOX_ID + "]");
		driver.findElement(By.id(AndroidHomeConst.BODY_INPUT_TEXTBOX_ID)).sendKeys(text);
	}
	
	/**
	 * Method to check if the button 1 is visible.
	 */
	public boolean checkButton1IsVisible() {
		LOGGER.info("Checking if the element is visible: [" + AndroidHomeConst.BODY_BUTTON_BUTTON1_ID + "]");
		return driver.findElement(By.id(AndroidHomeConst.BODY_BUTTON_BUTTON1_ID)).isDisplayed();
	}
	
	/**
	 * Method to click on the button 1
	 */
	public void clickOnButton1() {
		LOGGER.info("Clicking on the element: [" + AndroidHomeConst.BODY_BUTTON_BUTTON1_ID + "]");
		driver.findElement(By.id(AndroidHomeConst.BODY_BUTTON_BUTTON1_ID)).click();
	}
	
	/**
	 * Method to check if the button 2 is Enable and Clickable.
	 */
	public boolean waitForButton2IsEnableAndClickable() {
		LOGGER.info("Waiting for the element to be enabled and clickable: [" + AndroidHomeConst.BODY_BUTTON_BUTTON1_ID + "]");
		wait = new WebDriverWait(driver, 10);
		return wait.until(ExpectedConditions.elementToBeClickable(By.id(AndroidHomeConst.BODY_BUTTON_BUTTON1_ID))) != null;
	}
	
	/**
	 * Method to click on the button 2
	 */
	public void clickOnButton2() {
		LOGGER.info("Clicking on the element: [" + AndroidHomeConst.BODY_BUTTON_BUTTON2_ID + "]");
		driver.findElement(By.id(AndroidHomeConst.BODY_BUTTON_BUTTON2_ID)).click();
	}
	
	/**
	 * Method to click on the button 3
	 */
	public void clickOnButton3() {
		LOGGER.info("Clicking on the element: [" + AndroidHomeConst.BODY_BUTTON_BUTTON3_ID + "]");
		driver.findElement(By.id(AndroidHomeConst.BODY_BUTTON_BUTTON3_ID)).click();
	}
	
	/**
	 * Method to click on the button "Click here"
	 */
	public void clickOnButton4() {
		LOGGER.info("Clicking on the element: [" + AndroidHomeConst.BODY_BUTTON_BUTTON4_ID + "]");
		driver.findElement(By.id(AndroidHomeConst.BODY_BUTTON_BUTTON4_ID)).click();
	}
	
}
