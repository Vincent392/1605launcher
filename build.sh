#!/bin/sh
set -e
mkdir -p resources
wget -O resources/dirt.png https://github.com/exalpha-dev/exalpha-dev.github.io/raw/main/dirt.png
wget -O resources/dirt2.png https://github.com/exalpha-dev/exalpha-dev.github.io/raw/main/dirt2.png
wget -O resources/stone.png https://github.com/exalpha-dev/exalpha-dev.github.io/raw/main/stone.png

javac com/glowiak/extlauncher/Main.java

jar cvfe launcher_Fix.jar com.glowiak.extlauncher.Main com/glowiak/extlauncher/*.class resources/
