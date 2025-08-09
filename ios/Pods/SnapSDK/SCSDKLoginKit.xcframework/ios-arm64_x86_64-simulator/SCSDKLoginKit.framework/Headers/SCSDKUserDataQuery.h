//
//  SCSDKUserDataQuery.h
//  SCSDKLoginKit
//
//  Created by Madison Westergaard on 10/28/21.
//  Copyright © 2021 Snap, Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

/// SCSDKUserDataQuery object is used in fetchUserData method
@interface SCSDKUserDataQuery : NSObject

@property (nonatomic, copy) NSString *userDataQuery;

- (instancetype)initWithQuery:(NSString *)query;
- (instancetype)init NS_UNAVAILABLE;

@end
