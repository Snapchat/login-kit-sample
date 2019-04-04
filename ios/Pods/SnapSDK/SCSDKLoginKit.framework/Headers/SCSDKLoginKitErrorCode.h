//
//  SCSDKLoginKitErrorCode.h
//  SCSDKLoginKit
//
//  Created by Hongjai Cho on 3/17/19.
//  Copyright © 2019 Snap, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

#define SC_SDK_LOGINK_KIT_ERROR_DOMAIN @"SCSDKCreativeKitErrorDomain"

typedef NS_ENUM(NSInteger, SCSDKLoginKitErrorCode) {
    SCSDKLoginKitErrorCodeUnknown,
    SCSDKLoginKitErrorCodeRevokedSession,
    SCSDKLoginKitErrorCodeInvalidUser
};
