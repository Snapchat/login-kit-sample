//
//  SCSDKUserData.h
//  SCSDKLoginKit
//
//  Created by Madison Westergaard on 11/9/21.
//  Copyright © 2021 Snap, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/// Object that holds a user's fetched data.

@interface SCSDKUserData : NSObject

@property (nonatomic, readonly, nullable) NSString *displayName;
@property (nonatomic, readonly, nullable) NSString *externalID;
@property (nonatomic, readonly, nullable) NSString *idToken;
@property (nonatomic, readonly, nullable) NSString *bitmojiAvatarID;
@property (nonatomic, readonly, nullable) NSString *bitmojiTwoDAvatarUrl;
@property (nonatomic, readonly, nullable) NSString *profileLink;

- (instancetype)initWithDisplayName:(NSString *)displayName
                         externalID:(NSString *)externalID
                            idToken:(NSString *)idToken
                    bitmojiAvatarID:(NSString *)bitmojiAvatarID
               bitmojiTwoDAvatarUrl:(NSString *)bitmojiTwoDAvatarUrl
                        profileLink:(NSString *)profileLink;

@end

NS_ASSUME_NONNULL_END
