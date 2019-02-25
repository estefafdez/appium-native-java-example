# AppiumSDOS

Appium Archetype for SDOS with Maven.

The aim of this project is to provide a Maven archetype for all the QA Appium Projets.

<img src="http://oi67.tinypic.com/213264w.jpg" />
_______________________________________

## Prerequisites

1. [Appium installation and configuration.] (/Documentation/AppiumInstall.md)
2. [Maven configuration] (/Documentation/MavenConf.md)
3. [Java installation] (/Documentation/JavaInstall.md)
4. [Emulators] (/Documentation/Emulators.md) 
5. [POM examples (for iOS and Android).] (/Documentation/POM_Examples.md)

## How to start with the archetype

Firstly, you have to create a fork and clone the repository to get the project structure.

Secondly, change in the pom.xml the values according to the OS you need and your application name. 

Finally, enjoy coding!

## Archetype structure
The archetype consist of several important parts, everything has been mounted on the structure of a [Maven](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) project.

###### In the pom:

1. We have defined as a dependency all the properties necessary to run the project and to configure it. You can change the versions into the _pom.xml_ on the dependencies and plugin section.
2. Configure the test executions parameters: _device name, full reset, platform name, platform version, activity to wait, app name_...
3. Add the AppiumCore dependencies. In order to work with all the logic implemented on the [AppiumCore project](http://git.sdos.es/qa/appium-core) you need to make sure you have the latest version of it. You can see all the releases [here](http://git.sdos.es/qa/appium-core/tags) (_The dependences are already added for you, you just need to update the core on the new releases!_)

```bash
		<!--  Dependencies -->
		<appium-core.version>1.2-alpha</appium-core.version>
```

```bash
		<!-- Appium Core -->
		<dependency>
		    <groupId>org.sdos.appium</groupId>
		    <artifactId>appium-core</artifactId>
		    <version>${appium-core.version}</version>
		</dependency>
```

###### In the src/test/resources:
1. __Files:__ in this folder you need to copy the application you are going to test. You can include several apps inside (for iOS and Android for example).
2. __Selectors:__ In this .properties we store the way to access to every element of every page. Using ID and  Xpath. That method modulates and encapsulates these variables, with the advantage that entails.
3. __Suites:__ Here we can group sets of tests to run it together.
3. __test.properties & log4j2.properties__: Here we store some common tests properties and the lod4j2 properties. 

###### In the src/main/java:
1. __Constant:__ on this package we include every Constant class related to each Page Object. 
2. __PageObject:__ on this package we include every PageObject needed to create the tests.  

###### In the src/test/java:
On this package we create all the Test Classes in order to interact with every _PageObject_ and every _ConstantPage_. 

All test should start with _test_ and have a __self-descriptive name__, for example: _testLoginOnApp_.

__*** Remember__ do not forget to use the _AssertTrue_ or _AssertFalse_ on the checks methods and include the custom message for the error. 

One example of a test case is:

```bash
	/**
	 * First iOS Test
	 */
	@Test(description = "testIosExample")
	public void testIosExample() {

			//Create a new instance of the IosHomePage
			IosHomePage iosHomePage = new IosHomePage(driver);			
			
			//Wait until the Home Page is ready
			waitForReady(iosHomePage);
	
			//Click on the input "Enter your name"
			iosHomePage.clickOnInput();
				
			//Send a text into the input
			iosHomePage.sendKeyToInput("QA");
				
			//Click on the "Click!" button
			iosHomePage.clickOnButton();
				
			//Check if the Switch label is "Click on the switch button"
			assertTrue(iosHomePage.checkSwitchLabel("Click on the switch button:"), "The Label doesn't have the text: 'Click on the switch button'");
				
			//Click on the Switch button
			iosHomePage.clickOnSwitchButton();
				
			//Check if the Switch label is "OFF"
			assertTrue(iosHomePage.checkSwitchLabel("OFF"), "The Label doesn't have the text 'OFF'");
				
			//Click on the Switch button again
			iosHomePage.clickOnSwitchButton();
				
			//Check if the Switch label is "ON"
			assertTrue(iosHomePage.checkSwitchLabel("ON"), "The Label doesn't have the text 'ON'");
				
			//Click on the switch button
			iosHomePage.clickOnSwitchButton();
	}
```

### Items selectors
The selectors are _.properties_ files used to save the way to locate elements of the page (ID's, Xpath). To make it work the _.properties_ file should have exactly the same name of the PageObject referred with the next changes: the _.properties_ file name should go in lower case and without the suffix _Page_.

> Example:  
_AndroidHomePage.java_ --> _androidhome.properties_
>

Also, the elements defined inside the _.properties_ should have the structure:

>
android.viewGroup.id=action_bar
>

__android. or ios.__ _before_ the name.

__.id or .xpath__ _after_ the name.

## Run the test.
__Important:__  To run the test are absolutely necessary two conditions:  

>
1. The emulator should be running (only on Android, on iOS the MacOSX can create a new instance). 
2. Appium should be running.
>


## How to run a suite. 
To launch a suite you need to create a profile with its route as the example:

```
<profile>
	<id>acceptance-suite</id>
	<properties>
		<testNG.suite>src/test/resources/suites/acceptance.xml</testNG.suite>
	</properties>
</profile>
```
