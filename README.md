# Appium-native-java-example

Example on iOS and Android using Appium. The aim of this project is provide a maven project in order to run automated test with Android and iOS easily.
_______________________________________

## Prerequisites

1. [Homebrew installation](/Documentation/HomebrewConf.md)
2. [Maven configuration](/Documentation/MavenConf.md)
3. [Java installation](/Documentation/JavaInstall.md)
4. [Android SDK installation](/Documentation/AndroidSDKInstall.md)
5. [Appium installation and configuration.](/Documentation/AppiumInstall.md)
6. [Android and iOS Emulators](/Documentation/Emulators.md)
7. [POM examples (for iOS and Android).](/Documentation/POM_Examples.md)

## How to start with the project

First, you have to clone the repository to get the project structure.

Secondly, change in the pom.xml the values according to the OS you need and your application name. 

Finally, enjoy coding!

## Project structure
This project consist of several important parts, everything has been mounted on the structure of a [Maven](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) project.

###### In the pom:

1. We have defined as a dependency all the properties necessary to run the project and to configure it. You can change the versions into the _pom.xml_ on the dependencies and plugin section.
2. Configure the test executions parameters: _platform name and app name_

###### In the src/test/resources:
1. __Files:__ in this folder you need to copy the application you are going to test. You can include several apps inside (for iOS and Android for example).
2. __Suites:__ Here we can group sets of tests to run it together.
3. __test.properties & log4j2.properties__: Here we store some common tests properties and the log4j2 properties. 

###### In the src/main/java:
1. __Constant:__ on this package we include every Constant class related to each Page Object along with the ID or Xpath to find the elements. 
2. __PageObject:__ on this package we include every PageObject needed to create the tests.  

###### In the src/test/java:
On this package we create all the Test Classes in order to interact with every _PageObject_ and every _ConstantPage_. 

All test should start with _test_ and have a __self-descriptive name__, for example: _testLoginOnApp_.

__*** Remember__ do not forget to use the _AssertTrue_ or _AssertFalse_ on the checks methods and include the custom message for the error. 

One example of a test case is:

```bash
	/**
	 * Test to be an example for the first iOS Test using the TestApp.app application <br>
	 */
	@Test(description = "testIosExample")
	public void testIosExample() {

			/** Create a new instance of the IosHomePage */
			IosHomePage iosHomePage = new IosHomePage(driver);			
			
			/** Wait until the Home Page is ready */
			iosHomePage.waitForReadyPageByID();
	
			/** Click on the input "Enter your name" */
			iosHomePage.clickOnInput();
				
			/** Send a text into the input */
			iosHomePage.sendKeyToInput("QA Rocks"); 
				
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
```

## Run the test.
__Important:__  To run the test are absolutely necessary two conditions:  

>
1. The emulator should be running (only on Android, on iOS the MacOSX can create a new instance). 
2. Appium should be running.
>
