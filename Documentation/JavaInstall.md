# Java Installation

To install Java 8 you need to choose your OS and follow the instructions:

## MacOSX

We will use Homebrew to install Java. To install the latest version of java with cask type:
>
brew cask install java
>

To install a concrete version of java we will use the following command:
>
brew cask install homebrew/cask-versions/java8
>

* We suggest install version: 8.

Then we need to add the JAVA_HOME path into the bash_profile:

```bash
echo 'export JAVA_HOME="$(/usr/libexec/java_home)"' >> ~/.bash_profile

echo 'export PATH="$JAVA_HOME/bin:$PATH"' >> ~/.bash_profile

source ~/.bash_profile
```

## Windows 

Follow the instruction [here] (http://java-buddy.blogspot.com.es/2015/08/install-jdk-8-on-windows-10-and-set-path.html)

## Linux

Follow the instruction [here] (https://www.digitalocean.com/community/tutorials/como-instalar-java-con-apt-get-en-ubuntu-16-04-es)

## Check your Java Version:

Once Java is installed, check the version by typing on the terminal:
>
java -version
>
