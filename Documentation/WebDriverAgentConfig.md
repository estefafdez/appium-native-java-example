# WebDriverAgent Configuration on MacOSX.

To configure this project on MacOSX and Xcode you need to follow those steps:

## 1. Download the latest version.

Make sure you have the __latest__ version of the WebDriverAgent.

Download the project here: [https://github.com/facebook/WebDriverAgent](https://github.com/facebook/WebDriverAgent)

## 2. Make sure you have the project on the correct path.

Copy the project you just download it into the path:

>
cd "/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/"
>

Create the following folder:

>
mkdir -p Resources/WebDriverAgent.bundle
>

Once you have it, execute the Bootstrap Script:
>
./Scripts/bootstrap.sh
>

## 3. Open the project on Xcode.

Once you run the script, open the file __WebDriverAgent.xcodeproj__ with the Xcode.

<img src="http://git.sdos.es/qa/appium-archetype/raw/86d512c6d796b1f572adeb9f74187e8394106022/Documentation/img/1.png" />


Now, select the WebDriverAgent project and click on the __Info__ tab to change on the __Deployment Target__ , the __iOS Deployment Target__. Select the iOS version you want to test your application. 

<img src="http://git.sdos.es/qa/appium-archetype/raw/86d512c6d796b1f572adeb9f74187e8394106022/Documentation/img/2.png" />


On the __Build Settings__ tab, on the Deployment settings, change the __iOS Deployment Target__ as well, to match the same version as the Info tab.

<img src="http://git.sdos.es/qa/appium-archetype/raw/86d512c6d796b1f572adeb9f74187e8394106022/Documentation/img/3.png" />


Once you change the version, make sure you have a __simulator__ selected on the device list and click on __Product -> Test __. 
The project will compile and execute the WebDriverAgent project on the simulator you choose. 

<img src="http://git.sdos.es/qa/appium-archetype/raw/86d512c6d796b1f572adeb9f74187e8394106022/Documentation/img/4.png" />


Finally, close the Xcode and set on the archetype project the same version you select on the Project to run your application. 

If you want to change the version, e.g.: iOS 10.3 instead of iOS 11.0, you need to repeat all the steps of this tutorial. 

## 4. Problems. 

Sometimes, the WDA project have some problems to compile and to apply the iOS Version you selected. If you face any problem, clean the __DerivedData__ folder on the XCode. 

To clean the DerivedData folder you need to:

1. Click on Xcode -> Preferences -> Locations -> (->) DerivedData 

<img src="http://git.sdos.es/qa/appium-archetype/raw/86d512c6d796b1f572adeb9f74187e8394106022/Documentation/img/5.png" />


2. Once you have the DerivedData folder on the Finder, close the Xcode, and delete all the content inside the DerivedData folder.

<img src="http://git.sdos.es/qa/appium-archetype/raw/86d512c6d796b1f572adeb9f74187e8394106022/Documentation/img/6.png" />


3. Empty the Trash.

4. Open the Xcode and repeat all the steps again.

Also you can remove all the data from the command line typing:

```bash
rm -rf ~/Library/Developer/Xcode/DerivedData

rm -rf ~/Library/Caches/com.apple.dt.Xcode
```

