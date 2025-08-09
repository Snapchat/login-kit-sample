//
//  SCSDKLoginKitErrorCode.h
//  SCSDKLoginKit
//
//  Created by Hongjai Cho on 3/17/19.
//  Copyright © 2019 Snap, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

#define SC_SDK_LOGIN_KIT_REFRESH_TOKEN_ERROR_DOMAIN @"SCSDKLoginKitRefreshTokenErrorDomain"

typedef NS_ENUM(NSInteger, SCSDKLoginKitRefreshTokenErrorCode) {
    SCSDKLoginKitRefreshTokenErrorCodeNone,
    SCSDKLoginKitRefreshTokenErrorCodeTokenRevoked,
    SCSDKLoginKitRefreshTokenErrorCodeUnknown = 999
};

#define SC_SDK_LOGIN_KIT_USER_DATA_ERROR_DOMAIN @"SCSDKLoginKitUserDataErrorDomain"

typedef NS_ENUM(NSInteger, SCSDKLoginKitUserDataErrorCode) {
    SCSDKLoginKitUserDataErrorCodeNone,
    SCSDKLoginKitUserDataErrorCodeInternal,
    SCSDKLoginKitUserDataErrorCodeUnauthorizedUser,
    SCSDKLoginKitUserDataErrorCodeQueryValidation,
    SCSDKLoginKitUserDataErrorCodeUnknown
};
