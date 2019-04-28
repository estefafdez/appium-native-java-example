## 1. Install Homebrew on Mac. 

Homebrew URL: [https://brew.sh/index_es.html](https://brew.sh/index_es.html)

>
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
>

### Add Homebrew path to the ~/.bash_profile.

We need to add the location on the path in order to use the tools installed with Homebrew. Open your terminal and set:

```bash
echo 'export PATH="/usr/local/bin:$PATH"' >> ~/.bash_profile

echo 'export PATH="/usr/local/sbin:$PATH"' >> ~/.bash_profile

source ~/.bash_profile
```