# Login Kit x React Native

Login Kit sample code for React Native.

## Prerequisite

You need to have Snapchat Account before accessing any of the Snap Kit APIs. It's quick and easy, see [Creating a Snapchat Account](https://support.snapchat.com/en-US/a/account-setup).

## App Registration

1. Go to the [Snap Kit developer portal](https://kit.snapchat.com/portal/) > Sign-in with your Snapchat Account > Either create a new App by clicking on **New Project** Or Open an already existing app.
<p>

2. After the app is registered, click **Setup** and you should see two OAuth Client IDs:

   | OAuth Client ID | Usage                                                                                                                                                                             |
   | --------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | Production      | This lets your app post content from any Snapchat account but your app [must be approved](https://docs.snapchat.com/docs/review-guidelines) for the Production Client ID to work. |
   | Staging         | This lets your app post content even before an app is reviewed and approved but only Snapchat accounts listed under the `Demo Users` will be able to use your application.        |

## Snap Kit Setup

### Required

Below are the minimum pieces of bits you would need to set for this sample app to work.

#### 1. Update the Code with OAuth Client ID

Copy the OAuth Client ID for **Staging** and paste it in the code:

| Platform | Location                                                                                                                                              |
| -------- | ----------------------------------------------------------------------------------------------------------------------------------------------------- |
| Android  | Paste it under the `com.snapchat.kit.sdk.clientId` meta-data key defined in the [AndroidManifest.xml](android/app/src/main/AndroidManifest.xml) file. |
| iOS      | Paste it under the `SCSDKClientId` key defined in the [Info.plist](ios/ReactNativeLoginKitDemo/Info.plist) file.                                      |

#### 2. Add `Demo Users` on the Developer Portal

To test your Snap Kit integration on Staging we need to update the Demo Users section with:

| Username                      |
| ----------------------------- |
| <i>Your snapchat username</i> |

#### 3. Add `Platform Identifiers` on the Developer Portal

To allow your Android and iOS apps access Snap Kit APIs we need to update the Platform Identifiers section with:

| Stage   | Platform | Bundle or App ID                                   |
| ------- | -------- | -------------------------------------------------- |
| Staging | Android  | com.reactnativeloginkitdemo                        |
| Staging | iOS      | org.reactjs.native.example.ReactNativeLoginKitDemo |

<details>
  <summary markdown="span">
    <i>💁 For info on where these identifiers are specified, check</i>
  </summary>

| Platform | Location                                                                                                             |
| -------- | -------------------------------------------------------------------------------------------------------------------- |
| Android  | Specified as the `applicationId` under the `defaultConfig` tag in the [build.gradle](android/app/build.gradle) file. |
| iOS      | Refer the Bundle Identifier for your app in the [Info.plist](ios/ReactNativeLoginKitDemo/Info.plist) file.           |

</details>

#### 4. Enable `Login Kit` for your app's version

To be able to use [Login Kit](https://kit.snapchat.com/login-kit) we need to enable it on the developer portal.

1. Click under `Versions` and enable `Login Kit`.
<p>

2. Under the `User Permissions` and `Snapchat Verify` check all the options you would want access for.

   | For this sample app, check 👇 |
   | ----------------------------- |
   | ✓ Display Name                |
   | ✓ Bitmoji Avatar              |
   | ✓ Snapchat Verify             |

<p>

3. Update the `Redirect URIs for OAuth` section with:

   | URI                        |
   | -------------------------- |
   | snapkitexample://main/auth |

   <details>
     <summary markdown="span">
       <i>💁 For info on where the redirect url is specified, check</i>
     </summary>

   | Platform | Location                                                                                                                                                |
   | -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
   | Android  | Specied under the `com.snapchat.kit.sdk.redirectUrl` meta-data key defined in the [AndroidManifest.xml](android/app/src/main/AndroidManifest.xml) file. |
   | iOS      | Specied under the `SCSDKRedirectUrl` key defined in the [Info.plist](ios/ReactNativeLoginKitDemo/Info.plist) file.                                      |

   </details>

| <mark> Note: You don't need to submit this version for review for testing on `Staging` </mark> |
| ---------------------------------------------------------------------------------------------- |

#### 5. Activate the version on the Staging Environment

To be able to use your app you need to activate your version (<i> on which you enabled `Login Kit`</i>) on the Staging Environment.

Click **Setup** and update the **Active on Staging** version with your version on which you enabled Login Kit (<i> usually it's named `Initial Version` if you haven't renamed it</i>).

### (Optional) _If using your own sample app_

If you are using your own sample app you would need additional setup. This is already done for you in this sample app.

#### General

##### 1. Add the Snap Kit React Native dependency

```shell
$ yarn add @snapchat/snap-kit-react-native
```

#### Android

Always refer [public docs](https://kit.snapchat.com/docs/login-kit-android) for most up-to date info.

##### 1. Import the Login Kit from our Maven repository

Open up your apps [project-level build.gradle](android/build.gradle) file and add the following code block in the `repositories` section:

```xml
 repositories {
   maven {
     url "https://storage.googleapis.com/snap-kit-build/maven"
   }
 }
```

##### 2. Update your [AndroidManifest.xml](android/app/src/main/AndroidManifest.xml) file

You need to amend these entries within the `<application>` node:

```xml
<meta-data android:name="com.snapchat.kit.sdk.clientId" android:value="INSERT_YOUR_OAUTH_CLIENT_ID" />
<meta-data android:name="com.snapchat.kit.sdk.redirectUrl" android:value="INSERT_YOUR_OAUTH_REDIRECT_URL" />
<meta-data android:name="com.snapchat.kit.sdk.scopes" android:resource="@array/snap_connect_scopes" />
```

If your app's redirectUrl is `snapkitexample://main/auth` then your redirectUrl attribute would look like this:

```xml
<meta-data android:name="com.snapchat.kit.sdk.redirectUrl" android:value="snapkitexample://main/auth" />
```

You will also need to amend the `scheme`, `host` and `path` as per below:

```xml
  <activity
      android:name="com.snapchat.kit.sdk.SnapKitActivity"
      android:launchMode="singleTask">

      <intent-filter>
          <action android:name="android.intent.action.VIEW" />
          <category android:name="android.intent.category.DEFAULT" />
          <category android:name="android.intent.category.BROWSABLE" />

          <data
              android:scheme="snapkitexample"
              android:host="main"
              android:path="/auth"
              />
      </intent-filter>

  </activity>
```

If your app targets `Android 11 (API level 30)` or higher, you will also need to include the following package query:

```xml
<queries>
 <package android:name="com.snapchat.android" />
</queries>
```

##### 3. Define `Scopes` in your [res/values/arrays.xml](android/app/src/main/res/values/arrays.xml) file

Scopes let your application declare which Login Kit features it wants access to.

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
 <string-array name="snap_connect_scopes">
   <item>https://auth.snapchat.com/oauth2/api/user.display_name</item>
   <item>https://auth.snapchat.com/oauth2/api/user.bitmoji.avatar</item>
   <item>https://auth.snapchat.com/oauth2/api/user.external_id</item>
 </string-array>
</resources>
```

**📝 Note:** You need to enable these scopes on the developer portal under the `Login Kit` option to be able to access them.

#### iOS

Always refer [public docs](https://kit.snapchat.com/docs/login-kit-ios) for most up-to date info.

##### 1. Update your [Info.plist](ios/ReactNativeLoginKitDemo/Info.plist) file

| Key                         | Value                                                                                                                                                                                                                                                                                                              |
| --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| LSApplicationQueriesSchemes | This should contain `snapchat`, `bitmoji-sdk`, and `tms-apps`.                                                                                                                                                                                                                                                     |
| SCSDKClientId               | The OAuth Client ID you received from registering your app on the developer portal.                                                                                                                                                                                                                                |
| SCSDKRedirectUrl            | The URL that Snapchat will use to redirect users back to your app after a successful authorization.                                                                                                                                                                                                                |
| SCSDKScopes                 | OAuth2 scopes. Set it one or all of the following scopes for the resources you wish to retrieve from the user profile:<br><br> - https://auth.snapchat.com/oauth2/api/user.display_name<br> - https://auth.snapchat.com/oauth2/api/user.bitmoji.avatar<br> - https://auth.snapchat.com/oauth2/api/user.external_id |
| URL Types / Document Role   | Set it to `Editor`.                                                                                                                                                                                                                                                                                                |
| URL Types / URL identifier  | Set it to the app's Bundle ID ie. `$(PRODUCT_BUNDLE_IDENTIFIER)`.                                                                                                                                                                                                                                                  |
| URL Types / URL Schemes     | Set it to a unique string (without space) to allow Snapchat to redirect back to your app after a successful authorization.<br><br>For example, If your app's redirectUrl (refer the `SCSDKRedirectUrl` key) is `snapkitexample://main/auth` then your scheme would be `snapkitexample`.                            |

##### 2. Finishing Login with Snapchat

Once your user successfully authorizes your app to log in with Snapchat, you need to handle the deeplink that comes from Snapchat to get the access token.

In `AppDelegate.m`, use the `SCSDKLoginClient` interface to receive the deeplink:

```objective-c
#import <SCSDKLoginKit/SCSDKLoginKit.h>

- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
            options:(NSDictionary<UIApplicationOpenURLOptionsKey, id> *)options {
. . .
              if ([SCSDKLoginClient application:application openUrl:url options:options]) {
                return YES;
              }
. . .
}
```

## React Native Environment Setup

Always refer [React Native public docs](https://reactnative.dev/docs/environment-setup) for most up-to-date information.

1. Install [Homebrew](https://brew.sh/)
<p>

2. Install [Android Studio](https://developer.android.com/studio)
<p>

3. Install [Xcode](https://developer.apple.com/xcode/)
<p>

4. Install `JDK` (<i>can be any of Java 8 variant</i>)

   ```shell
   $ brew install --cask adoptopenjdk/openjdk/adoptopenjdk8
   ```

<p>

5. Add `ANDROID_HOME` to path

   ```shell
   $ export ANDROID_HOME=$HOME/Library/Android/sdk
   ```

<p>

6. Install `cocoapods`

   ```shell
   $ brew install cocoapods
   ```

<p>

7. Install `Node` & `Watchman`

   ```shell
   $ brew install node
   $ brew install watchman
   ```

<p>

8. Install `yarn`

   ```shell
   $ brew install yarn
   ```

## Running the Sample App

1. Clone the Sample App repository on your local machine

   ```shell
   # Clone the repo
   $ git clone https://github.com/Snapchat/login-kit-sample.git

   # CD into the React Native root directory
   $ cd login-kit-sample/react-native/
   ```

   **Note:** You should now be inside the **react-native root directory**: `/Users/.../login-kit-sample/react-native/`

<p>

2. Install `JS` dependencies

   ```shell
   $ yarn install
   ```

<p>

3. Install `pods`

   ```shell
   # CD into the "ios" directory
   $ cd ios

   # Install pods
   $ pod install

   # CD back to the root directory
   $ cd ..
   ```

<p>

4. Run the `ReactNativeLoginKitDemo` app

   ```shell
   # Running on Android
   $ yarn android

   # Running on iOS Simulator
   $ yarn ios

   # Running on iOS Physical Device (You can see the <device_name> from the Xcode)
   # Note that this will require:
   #   - Selecting a development team in the "Signing & Capabilities" editor in Xcode
   $ yarn ios --device "<device_name>"
   ```
