package org.estefafdez.appium.java.example;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.estefafdez.appium.java.example.config.TestSetConfig;
import org.estefafdez.appium.java.example.pageobjects.AndroidHomePage;

public class AndroidHomeTestSet extends TestSetConfig {
	
	/**
	 * Test to be an example for the first Android Test using the
	 * AndroidBaristaApp.apk application <br>
	 * 
	 * @see TestRail: <a href="testAndroidExample">
	 *      https://testrail.sdos.es/index.php </a>
	 */
	@Test(description = "testAndroidExample")
	public void testAndroidExample() {

		/** Create a new instance of the AndroidHomePage */
		AndroidHomePage androidHomePage = new AndroidHomePage(driver);

		/** Wait until the Home Page is ready */
		//androidHomePage.waitForReady();

		/** Perform a Long click on an element. */
		//androidHomePage.performLongClickOnInput(4);

		/** Send the text QA into the edit text */
		//androidHomePage.sendKeyToInput("QA");

		/** Check if button 1 is Visible */
		//Assert.assertTrue(androidHomePage.checkButton1IsVisible(), "The button 1 is not visible");

		/** Click on the button 1 */
		androidHomePage.clickOnButton1();

		/** Check if button 2 is enable and clickable. */
		Assert.assertTrue(androidHomePage.waitForButton2IsEnableAndClickable(),
				"The button 2 is not enable and clickable");

		/** Click on the button 2 */
		androidHomePage.clickOnButton2();

		/** Click on the button 3 */
		androidHomePage.clickOnButton3();

		/** Click on the button 4 */
		androidHomePage.clickOnButton4();
	}
}
