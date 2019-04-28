# POM Examples

In order to understand better the project, you can find an example of the POM Values for an Android Test and for an iOS Test. 

## Example of POM on iOS
```bash

	<!-- ++++++++++++++++++++++++++++++ TEST EXECUTIONS PARAMETERS ++++++++++++++++++++++++++++++  -->
		
		<!-- Which mobile OS platform to use -->
		<devicePlatformName>iOS</devicePlatformName>
		
		<!-- App properties -->
		<!-- The .ipa or .apk file -->
		<deviceApp>TestApp.app</deviceApp>

```

## Example of POM on Android
```bash

	<!-- ++++++++++++++++++++++++++++++ TEST EXECUTIONS PARAMETERS ++++++++++++++++++++++++++++++  -->
		
		<!-- Which mobile OS platform to use -->
		<devicePlatformName>Android</devicePlatformName>
		
		<!-- App properties -->
		<!-- The .ipa or .apk file -->
		<deviceApp>AndroidBaristaApp.apk</deviceApp>

```