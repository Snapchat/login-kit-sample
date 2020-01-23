# Login Kit

Login Kit sample code for iOS.

## App Registration

You need to register your app at the [Snap Kit developer portal](https://snapkit.com) with the `bundleID` associated with your app.

App registration is an important part of the workflow. Creative Kit requires both the `bundleID` and a `clientID`. At runtime, Snapchat will verify that the content to be shared is redirected from a legitimate app.

Two OAuth2 `clientIDs` will be automatically generated when you register an app on the [Snap Kit developer portal](https://snapkit.com). You will receive a *Production* `clientID` and a *Development* `clientID`.

You can use the *Development* `clientID` anytime even before an app is approved. But the content can only be posted from a Snapchat account that is registered under the *Demo Users* in the app registration/profile page on [Snap Kit developer portal](https://snapkit.com).

With the *Production* `clientID`, your app can post the content from any Snapchat account. But your app must be approved for the *Production* `clientID` to work.

## Setup

1. (Optional) You can run bundler to install Cocoapods.

  ```bash
  $ bundle install
  ```

1. Install Snap Kit SDK via [Cocoapods](https://cocoapods.org/).

  ```bash
  $ pod install
  ```

1. Open the project in Xcode.

  ```bash
  $ open LoginKitSample.xcworkspace
  ```

1. Open Info.plist and modify the following attributes:

   * `URL Types`/`Document Role` - Set it to `Editor`
   * `URL Types`/`URL identifier` - Set it to the app's `bundleID` ie. `$(PRODUCT_BUNDLE_IDENTIFIER)`
   * `URL Types`/`URL Schemes` - Set it to a unique string (without space) allow Snapchat to redirect to your app after
   * `SCSDKClientId` - OAuth2 client ID you receive from registering your app
   * `SCSDKRedirectUrl` - the URL that Snapchat will use to redirect users back to your after a successful authorization
   * `LSApplicationQueriesSchemes` - Always set the value to `snapchat`
   * `SCSDKScopes` - OAuth2 scopes. Set it one or all of the following scopes for the resources you wish to retrieved from the user profile:
     * `https://auth.snapchat.com/oauth2/api/user.display_name`
     * `https://auth.snapchat.com/oauth2/api/user.external_id`
     * `https://auth.snapchat.com/oauth2/api/user.bitmoji.avatar`

1. Save your project.
1. Build and run the app on an iPhone.

## License

Copyright (c) Snap Inc., 2019. [License](LICENSE).
