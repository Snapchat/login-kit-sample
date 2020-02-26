//
//  LoginViewController.swift
//  LoginKitSample
//
//  Created by Samuel Chow on 1/9/19.
//  Copyright © 2019 Snap Inc. All rights reserved.
//

import UIKit

import SCSDKLoginKit

class LoginViewController: UIViewController {
    // MARK: - Properties
    
    fileprivate static let DefaultMessage = """
Login Kit lets your users authenticate with Snapchat and bring their existing
identity into your app. It uses OAuth, with Snapchat as the identity provider.
"""
    
    @IBOutlet fileprivate weak var loginButton: UIButton?
    @IBOutlet fileprivate weak var messageLabel: UILabel?
    @IBOutlet fileprivate weak var loginView: UIView?
    @IBOutlet fileprivate weak var profileView: UIView?
    @IBOutlet fileprivate weak var avatarView: UIImageView?
    @IBOutlet fileprivate weak var nameLabel: UILabel?
    @IBOutlet fileprivate weak var logoutButton: UINavigationItem?
}

// MARK: - Private Helpers

extension LoginViewController {
    fileprivate func displayForLogoutState() {
        // Needs to be on the main thread to control the UI.
        DispatchQueue.main.async {
            self.logoutButton?.rightBarButtonItem?.isEnabled = false
            self.loginView?.isHidden = false
            self.profileView?.isHidden = true
            self.messageLabel?.text = LoginViewController.DefaultMessage
        }
    }
    
    fileprivate func displayForLoginState() {
        // Needs to be on the main thread to control the UI.
        DispatchQueue.main.async {
            self.logoutButton?.rightBarButtonItem?.isEnabled = true
            self.loginView?.isHidden = true
            self.profileView?.isHidden = false
            self.messageLabel?.text = LoginViewController.DefaultMessage
        }
        
        displayProfile()
    }
    
    fileprivate func displayProfile() {
        let successBlock = { (response: [AnyHashable: Any]?) in
            guard let response = response as? [String: Any],
                let data = response["data"] as? [String: Any],
                let me = data["me"] as? [String: Any],
                let displayName = me["displayName"] as? String,
                let bitmoji = me["bitmoji"] as? [String: Any] else { return }
            
            // Needs to be on the main thread to control the UI.
            DispatchQueue.main.async {
                if let avatar = bitmoji["avatar"] as? String {
                    self.loadAndDisplayAvatar(url: URL(string: avatar))
                }
                self.nameLabel?.text = displayName
            }
        }
        
        let failureBlock = { (error: Error?, success: Bool) in
            if let error = error {
                print(String.init(format: "Failed to fetch user data. Details: %@", error.localizedDescription))
            }
        }
        
        let queryString = "{me{externalId, displayName, bitmoji{avatar}}}"
        SCSDKLoginClient.fetchUserData(withQuery: queryString,
                                       variables: nil,
                                       success: successBlock,
                                       failure: failureBlock)
    }
    
    fileprivate func loadAndDisplayAvatar(url: URL?) {
        DispatchQueue.global().async {
            guard let url = url,
                let data = try? Data(contentsOf: url),
                let image = UIImage(data: data) else {
                    return
            }
            
            // Needs to be on the main thread to control the UI.
            DispatchQueue.main.async {
                self.avatarView?.image = image
            }
        }
        
    }
}

// MARK: - Action Handlers

extension LoginViewController {
    @IBAction func loginButtonDidTap(_ sender: UIButton) {
        SCSDKLoginClient.login(from: self.navigationController!) { (success: Bool, error: Error?) in
            if success {
                // Needs to be on the main thread to control the UI.
                self.displayForLoginState()
            }
            if let error = error {
                // Needs to be on the main thread to control the UI.
                DispatchQueue.main.async {
                    self.messageLabel?.text = String.init(format: "Login failed. Details: %@", error.localizedDescription)
                }
            }
        }
    }
    
    @IBAction func logoutButtonDidTap(_ sender: UIBarButtonItem) {
        SCSDKLoginClient.clearToken()
    }
}

// MARK: - UIViewController

extension LoginViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        SCSDKLoginClient.addLoginStatusObserver(self)
        
        if SCSDKLoginClient.isUserLoggedIn {
            displayForLoginState()
        } else {
            displayForLogoutState()
        }
    }
}

extension LoginViewController: SCSDKLoginStatusObserver {
    func scsdkLoginDidUnlink() {
        self.displayForLogoutState()
    }
}

