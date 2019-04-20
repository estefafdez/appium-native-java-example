# Emulators

## Android Emulator.

* Launch the SDK, typing __android__ in the console.
* Create an emulator by typing: android create avd -n __nameEmulator__ --force -k "__emulatorImage;sdkVersion;emulatorArchitecture__"; e.g.:

>
android create avd -n testAndroid --force -k "system-images;android-27;google_apis_playstore;x86";
>

* To check the  list of emulator you have created, you can type in the console:

>
./emulator -list-avds
>

* To launch the emulator, you can type on the console:

>
./emulator @nameEmulator
>

If you already have an emulator, you can create a script to run it instead of type all the commands, e.g.:

```
#!/bin/bash

echo "Going to the emulator folder"
cd /Users/emunoz/Library/Android/sdk/emulator
echo "Launching Nexus 5X emulator..."
./emulator @Nexus_5X_API_27
```


## iOS Simulator.

On MacOSX, the iOS Simulators are included on the Xcode. 

You just need to download the latest version of Xcode and follow the instruction [here] (https://developer.apple.com/library/content/documentation/IDEs/Conceptual/iOS_Simulator_Guide/TestingontheiOSSimulator/TestingontheiOSSimulator.html#//apple_ref/doc/uid/TP40012848-CH4-SW1)
