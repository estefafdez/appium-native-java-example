package org.estefafdez.appium.java;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.estefafdez.appium.java.config.TestSetConfig;
import org.estefafdez.appium.java.pageobjects.IosHomePage;
import org.testng.annotations.Test;

public class IosHomeTestSet extends TestSetConfig {

	/**
	 * Test to be an example for the first iOS Test using the TestApp.app application <br>
	 * @see TestRail: <a href="testIosExample"> https://testrail.sdos.es/index.php </a>
	 */
	@Test(description = "testIosExample")
	public void testIosExample() {

			/** Create a new instance of the IosHomePage */
			IosHomePage iosHomePage = new IosHomePage(driver);			
			
			/** Wait until the Home Page is ready */
			waitForReady(iosHomePage);
	
			/** Click on the input "Enter your name" */
			iosHomePage.clickOnInput();
				
			/** Send a text into the input */
			iosHomePage.sendKeyToInput("QA"); 
				
			/** Click on the "Click!" button */
			iosHomePage.clickOnButton();
			
			/** Check Switch button. By default is enable */
			assertTrue(iosHomePage.checkSwitchButtonStatus(), "The Switch button is not enabled");
				
			/** Check if the Switch label is "Click on the switch button" */
			assertTrue(iosHomePage.checkSwitchLabel("Click on the switch button:"), "The Label does not have the text: 'Click on the switch button'");
				
			/** Click on the Switch button */
			iosHomePage.clickOnSwitchButton();
			
			/** Check Switch button. It should be disabled */
			assertFalse(iosHomePage.checkSwitchButtonStatus(), "The Switch button is not disabled");
				
			/** Check if the Switch label is "OFF" */
			assertTrue(iosHomePage.checkSwitchLabel("OFF"), "The Label does not have the text 'OFF'");
				
			/** Click on the Switch button again */
			iosHomePage.clickOnSwitchButton();
			
			/** Check Switch button, it should be enabled */
			assertTrue(iosHomePage.checkSwitchButtonStatus(), "The Switch button is not enabled");
				
			/** Check if the Switch label is "ON" */
			assertTrue(iosHomePage.checkSwitchLabel("ON"), "The Label does not have the text 'ON'");
	}

}
