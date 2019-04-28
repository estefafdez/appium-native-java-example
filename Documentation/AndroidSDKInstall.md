# Android SDK Installation

To install the Android SDK you can choose between install it with command line or with the GUI.

## Install by GUI.

To install the Android SDK Tool you need to:

* Download the Android SDK from [http://developer.android.com/intl/es/sdk/installing/index.html](http://developer.android.com/intl/es/sdk/installing/index.html)
* Choose the __"Stand-Alone Sdk Tools"__ option.
* After downloading it, unzip the SDK and locate it in the desired folder (e.g.: /Users/youruser/Library/Android/sdk)
* Set the ANDROID_HOME in the environment variables. Example:

```bash
export ANDROID_HOME=/Users/youruser/Library/Android/sdk
```

## Install by command line. 

Follow the following commands to install the Android SDK by command line using Homebrew. 

```bash
brew cask install android-sdk
```

Add the ANDROID_HOME into the bash profile:

```bash
echo 'export ANDROID_HOME="/usr/local/share/android-sdk"' >> ~/.bash_profile

echo 'export PATH="$ANDROID_HOME/tools:$PATH"' >> ~/.bash_profile

echo 'export PATH="$ANDROID_HOME/platform-tools:$PATH"' >> ~/.bash_profile

echo 'export PATH="$ANDROID_HOME/platform-tools/adb:$PATH"' >> ~/.bash_profile

source ~/.bash_profile
```

Create the repositories.cfg file and update the SDK:

```bash
touch ~/.android/repositories.cfg

sdkmanager --update

sdkmanager --licenses
```

Install the packages you need to create android emulators:

```bash
sdkmanager --list

sdkmanager "platform-tools"

sdkmanager "build-tools;28.0.0"

sdkmanager "platforms;android-27"
```
