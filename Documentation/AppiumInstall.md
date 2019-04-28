# Appium installation and configuration.

This steps are adapted to MacOSX, running Appium test for Android and iOS emulator. 

## Install NodeJS.

Download the LTS version here:  [https://nodejs.org/es/download/](https://nodejs.org/es/download/)

Or install node with Homebrew:

>
brew install node
>

* We suggest install version: v10.6.0.

## Install Carthage
>
brew install carthage
>

## Install the latest version of Appium Server (command line):
>
npm install -g appium --unsafe-perm=true --allow-root
>

To install a concrete version you can use: 
>
npm install -g appium@1.12.1 --unsafe-perm=true --allow-root
>

* We suggest install version: 1.12.1.

The _--unsafe-perm=true_ and _--allow-root_ are necessary sometimes. 

To check the Appium version installed, you need to run the command:

>
appium -v
>

## Install Appium Doctor

Appium Doctor is a library that check if everything you need to start with Appium is properly installed. You can use it with Android and iOS. 

Install Appium Doctor using Homebrew:

>
npm install -g appium-doctor
>

Check if everything you need to start with Appium and Android is correct:

>
appium-doctor --android --yes
>
Check if everything you need to start with Appium and iOS is correct:

>
appium-doctor --ios --yes
>

## Install WebDriverAgent. 

The WebDriverAgent is already installed when you installed Appium, but we need to configure it first in order to be able to use it. 

URL of the project: [https://github.com/facebook/WebDriverAgent](https://github.com/facebook/WebDriverAgent)

To configure the WebDriverAgent project you need to: 

Go to Appium-XCUITest and WebDriverAgent folder:

>
cd "/usr/local/lib/node_modules/appium/node_modules/appium-xcuitest-driver/WebDriverAgent/"
>

Create the following folder:

>
mkdir -p Resources/WebDriverAgent.bundle
>

Execute bootstrap script:
>
./Scripts/bootstrap.sh
>

To configure the WebDriverAgent project, you need to follow the instruction [here](/Documentation/WebDriverAgentConfig.md)

## Install the libimobiledevice library.
>
brew install libimobiledevice --HEAD
>

## Install ideviceinstaller, only works for iOS 9.
>
brew install ideviceinstaller
>

## Install ios-deploy
>
npm install -g ios-deploy --unsafe-perm=true --allow-root
>

## Install xcpretty
>
sudo gem install xcpretty
>

## Install appium-ios-driver
>
npm install appium-ios-driver
>

## Install the latest version of Appium Desktop.

If you prefer to have the Appium Desktop version, you can download it here: [https://github.com/appium/appium-desktop/releases](https://github.com/appium/appium-desktop/releases)

# Running Appium.

To starts Appium you just have to type __appium__ in a console and Appium process will start up.

>
appium
[Appium] Welcome to Appium v1.12.1
[Appium] Appium REST http interface listener started on 0.0.0.0:4723
>

# Stop Appium

To stop Appium on your console, you need to type __killall node__ and the Appium process will stop.

>
[Appium] Received SIGTERM - shutting down
>