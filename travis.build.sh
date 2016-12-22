#!/bin/sh
TRAVIS_BRANCH="v1.0.0-beta.5"
if [ "$TRAVIS_BRANCH" == "beta" ];then ./gradlew assembleBeta;elif [[ "$TRAVIS_BRANCH" == v*-beta.* ]];then ./gradlew assembleBeta;else ./gradlew assembleRelease;fi