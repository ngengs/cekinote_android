#!/bin/bash

if [[ $TRAVIS_BRANCH == 'beta' ]]
  "./gradlew assembleBeta"
else
  "./gradlew assembleRelease"
fi