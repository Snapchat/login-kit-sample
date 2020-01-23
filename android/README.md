# Login Kit

Login Kit sample code for Android.

## App Registration

You need to register your app at the [Snap Kit developer portal](https://kit.snapchat.com).

After registering your app you will receive two OAuth clientIDs, you will receive a *Production* `clientID` and a *Development* `clientID`.

You can use the *Development* `clientID` anytime even before an app is reviewed and approved. But when using the *Development* `clientID` only accounts listed under the *Demo Users* in the app registration/profile page on [Snap Kit developer portal]([https://kit.snapchat.com/portal](https://kit.snapchat.com/portal)) will be able to use your application.

With the *Production* `clientID`, your app can post the content from any Snapchat account. But your app must be approved for the *Production* `clientID` to work.

You also need to define a `redirectUrl` for your app, this is used within the AndroidManifest.xml

## Setup
Steps 1 and 2 are already complete in this demo app and are just here for your understanding, step 3 needs to be carried out before you can run the application.

1. You need to import the Login Kit and Core Kit from our Maven repository.
Open up your apps [Project level build.gradle](/android/build.gradle) and add the following code block in the repositories section.

```
repositories {
   maven {
       url "https://storage.googleapis.com/snap-kit-build/maven"
   }
}
```

2. Open up your [App level build.gradle](`/android/app/build.gradle`) and update the `snapKitSdkVersion` as necessary`.

```
ext {
    snapKitSdkVersion = '1.4.0'
}
```

3. You will also need to amend your `clientID` and `redirectUrl` in the appropriate meta-data tags in your application’s AndroidManifest.xml (/android/app/src/main/AndroidManifest.xml).

You need to amend these entries within the ```<application>``` node

        <meta-data android:name="com.snapchat.kit.sdk.clientId" android:value="INSERT_YOUR_OAUTH_CLIENT_ID" />
        <meta-data android:name="com.snapchat.kit.sdk.redirectUrl" android:value="INSERT_YOUR_OAUTH_REDIRECT_URL" />
        <meta-data android:name="com.snapchat.kit.sdk.scopes" android:resource="@array/snap_connect_scopes" />

If your app redirectUrl is `myapp://snap-kit/oauth2` then your `redirectUrl` attribute would look like this
```
<meta-data android:name="com.snapchat.kit.sdk.redirectUrl" android:value="myapp://snap-kit/oauth2" />
```

You will also need to amend the scheme, host and path as per below:
<intent-filter>
       <data
           android:scheme="the scheme of your redirect url"
           android:host="the host of your redirect url"
           android:path="the path of your redirect url"
           />
   </intent-filter>

If your redirect url is `myapp://snap-kit/oauth2` Then the breakdown is: `android:scheme="myapp" android:host="snap-kit" android:path="oauth2"`




