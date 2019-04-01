#!/bin/sh
if [ "$#" -gt 1 ]; then
    javac *.java
    java Main "$1" "$2"
else
    javac *.java
    java Main "$1"
fi
