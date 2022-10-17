//
//  SCSDKLoginButton.h
//  SCSDKLoginKit
//
//  Copyright © 2018 Snap, Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@protocol SCSDKLoginButtonDelegate
- (void)loginButtonDidTap;
@end

/// Standalone button that can be used to add Login With Snapchat functionality
@interface SCSDKLoginButton : UIView

/// Delegate that will receive LoginButton callbacks
@property (nonatomic, weak, nullable) id<SCSDKLoginButtonDelegate> delegate;

- (instancetype)initWithCompletion:(nullable void (^)(BOOL success, NSError *error))completion
    NS_DESIGNATED_INITIALIZER;
- (instancetype)initWithFrame:(CGRect)frame NS_UNAVAILABLE;
- (nullable instancetype)initWithCoder:(NSCoder *)coder NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
