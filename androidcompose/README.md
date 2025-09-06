# Login Kit  Jetpack Compose

Login Kit sample code for Android.

## App Registration

You need to register your app at the [Snap Kit developer portal](https://kit.snapchat.com).

After registering your app you will receive two OAuth clientIDs, you will receive a *Production* `clientID` and a *Development* `clientID`.

You can use the *Development* `clientID` anytime even before an app is reviewed and approved. But when using the *Development* `clientID` only accounts listed under the *Demo Users* in the app registration/profile page on [Snap Kit developer portal]([https://kit.snapchat.com/portal](https://kit.snapchat.com/portal)) will be able to use your application.

With the *Production* `clientID`, your app can post the content from any Snapchat account. But your app must be approved for the *Production* `clientID` to work.

You also need to define a `redirectUrl` for your app, this is used within the AndroidManifest.xml

## Setup
Steps 1 and 2 are already complete in this demo app and are just here for your understanding, step 3 needs to be carried out before you can run the application.

1. You need to import the Login Kit .

2. Open up your [App level build.gradle](`/androidcompose/app/build.gradle`) and add.

```kotlin 
android {
	defaultConfig {
		// other configrations
		gradleLocalProperties(rootDir).also {  
		  manifestPlaceholders.putAll(arrayOf(  
		            "SNAP_CLIENT_ID" to it.getProperty("com.snap.kit.clientId"),  
		            "SNAP_REDIRECT_URL" to it.getProperty("com.snap.kit.redirectUrl"),  
		            "REDIRECT_URL_HOST" to it.getProperty("com.snap.kit.host"),  
		            "REDIRECT_URL_PATH" to it.getProperty("com.snap.kit.path"),  
		            "REDIRECT_URL_SCHEME" to it.getProperty("com.snap.kit.scheme"),  
		        ))  
		}  
	}
}
dependencies {
	implementation("com.snap.loginkit:loginkit:3.0.0")
}
```  

1. Add the following in your `local.properties`.
```properties
com.snap.kit.clientId=YOUR_CLIENT_ID  
com.snap.kit.redirectUrl=YOUR_REDIRECT_URL  
com.snap.kit.host=YOUR_REDIRECT_URL_HOST  
com.snap.kit.path=/YOUR_REDIRECT_URL_PATH  
com.snap.kit.scheme=YOUR_REDIRECT_URL_SCHEME
```