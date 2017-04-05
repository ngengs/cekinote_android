# Ceki Note
[![Build Status](https://travis-ci.org/ngengs/cekinote_android.svg?branch=master)](https://travis-ci.org/ngengs/cekinote_android)

This is application for note the game score of "Ceki", the card game I always played with my friends.

### Building
To build this application prepare some file

1. file-keys.jks (the name is up to you)
   
   To craete the file you can read at [here](https://developer.android.com/studio/publish/app-signing.html) or [here](http://stackoverflow.com/questions/3997748/how-can-i-create-a-keystore)
   
   This file location will be used as *KEY_STORE_LOCATION* inside the keystore.properties file
   
2. keystore.properties
   
   With value
   
   ````
   RELEASE_KEY_PASSWORD=*KEY_PASSWORD*
   RELEASE_KEY_ALIAS=*KEY_ALIAS*
   RELEASE_STORE_PASSWORD=*KEY_STORE_PASSOWRD*
   RELEASE_STORE_LOCATION=*KEY_STORE_LOCATION (ex: file-keys.jks)*
   ````
   
And after all the prepared file is complete you can build with command
````
gradlew clean assembleRelease
````
to build release version, or
````
gradlew clean assembleBeta
````
to build beta version

### Library
This application use some library
* Google Support Library
* [Material Dialogs](https://github.com/afollestad/material-dialogs)
* [Greendao](https://github.com/greenrobot/greenDAO)
* [Butterknife](https://github.com/JakeWharton/butterknife)

### Download
Check the [release](../../releases/latest)

### Author
**Rizky Kharisma**

### License

    Copyright (C) 2016 Rizky Kharisma

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
