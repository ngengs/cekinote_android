#!/bin/bash

if [ $TRAVIS_BRANCH == "beta" ]
  then
    ./gradlew assembleBeta
else
  ./gradlew assembleRelease
fi