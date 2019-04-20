# POM Examples

In order to understand better the project, you can find an example of the POM Values for an Android Test and for an iOS Test. 

## Example of POM on Android
```bash
		<!-- The kind of mobile device or emulator to use -->
		<deviceName>Nexus_5X_API_27</deviceName>
		<!-- If the device should do a full reset before start -->
		<deviceFullReset>true</deviceFullReset>
		<!-- Which mobile OS platform to use -->
		<devicePlatformName>Android</devicePlatformName>
		<!-- Mobile OS version -->
		<devicePlatformVersion>8.1.0</devicePlatformVersion>
		
		<!-- Device Android properties -->
		<!-- Activity name for the Android activity you want to wait for -->
		<androidAppWaitActivity></androidAppWaitActivity>>		
		<!-- Name of the first Activity to launch the Android App -->
		<androidAppActivity></androidAppActivity>
		<!-- Package of the Android App -->
		<appPackage></appPackage>

		<!-- Device iOS properties -->
		<!-- The display name of the application under test. Used to automate backgrounding the app in iOS 9+ -->
		<iosAppName></iosAppName>
		<!-- Enable the XCode log-->
		<iosShowXcodeLog>true</iosShowXcodeLog>
		<!-- Enable the iOS Emulator log-->
		<iosShowiOSLog>false</iosShowiOSLog>

		<!-- App properties -->
		<!-- The .ipa or .apk file -->
		<deviceApp>AndroidBaristaApp.apk</deviceApp>
		<!-- Which app is hybrid (contains embedded content) -->
		<deviceAppHybrid>false</deviceAppHybrid>
```

## Example of POM on iOS
```bash
		<!-- Device common properties -->
		<!-- The kind of mobile device or emulator to use -->
		<deviceName>iPhone 7</deviceName>
		<!-- If the device should do a full reset before start -->
		<deviceFullReset>true</deviceFullReset>
		<!-- Which mobile OS platform to use -->
		<devicePlatformName>iOS</devicePlatformName>
		<!-- Mobile OS version -->
		<devicePlatformVersion>11.0</devicePlatformVersion>
		
		<!-- Device Android properties -->
		<!-- Activity name for the Android activity you want to wait for -->
		<androidAppWaitActivity></androidAppWaitActivity>>		
		<!-- Name of the first Activity to launch the Android App -->
		<androidAppActivity></androidAppActivity>
		<!-- Package of the Android App -->
		<androidAppPackage></androidAppPackage>

		<!-- Device iOS properties -->
		<!-- The display name of the application under test. Used to automate backgrounding the app in iOS 9+ -->
		<iosAppName></iosAppName>
		<!-- Enable the XCode log-->
		<iosShowXcodeLog>true</iosShowXcodeLog>
		<!-- Enable the iOS Emulator log-->
		<iosShowiOSLog>false</iosShowiOSLog>

		<!-- App properties -->
		<!-- The .ipa or .apk file -->
		<deviceApp>TestApp.app</deviceApp>
		<!-- Which app is hybrid (contains embedded content) -->
		<deviceAppHybrid>false</deviceAppHybrid>
```