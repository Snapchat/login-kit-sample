package com.thomasphillips3.goldenfold

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.snapchat.kit.sdk.SnapLogin
import com.snapchat.kit.sdk.core.controller.LoginStateController.OnLoginStateChangedListener
import com.snapchat.kit.sdk.login.models.MeData
import com.snapchat.kit.sdk.login.models.UserDataResponse
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback

class MainActivity : AppCompatActivity() {
    private lateinit var mContentView: View
    private lateinit var mLoginButton: View
    private lateinit var mLoginStateChangedListener: OnLoginStateChangedListener
    private lateinit var mDisplayName: TextView
    private lateinit var mExternalIdView: TextView
    private lateinit var meData: MeData
    private lateinit var mAvatarImageView: ImageView
    private lateinit var mSignOutButton: Button
    private lateinit var mLowerLayout: View
    private lateinit var mLabelMyProfile: TextView
    private lateinit var mLabelDisplayName: TextView
    private lateinit var mLabelExternalId: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // we define the various different visuals we will be using to display information about the user
        mContentView = findViewById(R.id.contentView)
        mLowerLayout = findViewById(R.id.lowerLayout)
        mLoginButton = SnapLogin.getButton(this, mLowerLayout as ViewGroup?)
        mDisplayName = findViewById(R.id.displayNameView)
        mExternalIdView = findViewById(R.id.externalIDView)
        mAvatarImageView = findViewById(R.id.avatarImageView)
        mSignOutButton = findViewById(R.id.signOutButton)
        mLabelMyProfile = findViewById(R.id.labelMyProfile)
        mLabelDisplayName = findViewById(R.id.labelDisplayNameView)
        mLabelExternalId = findViewById(R.id.labelExternalIdView)

        // the sign out button will allow the user to unlink their profile from the app
        mSignOutButton.setOnClickListener(View.OnClickListener { signOutUser() })

        // the LoginStateListener tells us when a user has signed in/out
        mLoginStateChangedListener = object : OnLoginStateChangedListener {
            override fun onLoginSucceeded() {
                Log.d("SnapkitLogin", "Login was successful")
                mSignOutButton.setVisibility(View.VISIBLE)
                mDisplayName.setVisibility(View.VISIBLE)
                mExternalIdView.setVisibility(View.VISIBLE)
                mAvatarImageView.setVisibility(View.VISIBLE)
                mLabelMyProfile.setVisibility(View.INVISIBLE)
                mLoginButton.setVisibility(View.INVISIBLE)
                mLabelDisplayName.setVisibility(View.VISIBLE)
                mLabelExternalId.setVisibility(View.VISIBLE)
                userDetails
            }

            override fun onLoginFailed() {
                Log.d("SnapkitLogin", "Login was unsuccessful")
                mDisplayName.setText(R.string.not_logged_in)
            }

            override fun onLogout() {
                // when the user unlinks their account we reset the fields and make the login button visible
                Log.d("SnapkitLogin", "User logged out")
                resetUserInfo()
            }
        }
        SnapLogin.getLoginStateController(this).addOnLoginStateChangedListener(mLoginStateChangedListener)
    }// set the value of the display name

    // set the value of the external id

    // not all users have a bitmoji connected, if the account has bitmoji connected we load the bitmoji avatar
    // set a list of the data the app wants to use - these need to mirror the snap_connect_scopes set in arrays.xml
    private val userDetails: Unit
        private get() {
            val isUserLoggedIn = SnapLogin.isUserLoggedIn(this)
            if (isUserLoggedIn) {
                Log.d("SnapkitLogin", "The user is logged in")

                // set a list of the data the app wants to use - these need to mirror the snap_connect_scopes set in arrays.xml
                val query = "{me{bitmoji{avatar},displayName,externalId}}"
                SnapLogin.fetchUserData(this, query, null, object : FetchUserDataCallback {
                    override fun onSuccess(userDataResponse: UserDataResponse?) {
                        if (userDataResponse == null || userDataResponse.data == null) {
                            return
                        }
                        meData = userDataResponse.data.me
                        if (meData == null) {
                            return
                        }

                        // set the value of the display name
                        mDisplayName!!.text = userDataResponse.data.me.displayName

                        // set the value of the external id
                        mExternalIdView!!.text = userDataResponse.data.me.externalId

                        // not all users have a bitmoji connected, if the account has bitmoji connected we load the bitmoji avatar
                        if (meData!!.bitmojiData != null) {
                            Glide.with(baseContext).load(meData!!.bitmojiData.avatar).into(mAvatarImageView!!)
                        }
                    }

                    override fun onFailure(isNetworkError: Boolean, statusCode: Int) {
                        Log.d("SnapkitLogin", "No user data fetched $statusCode")
                    }
                })
            }
        }

    private fun resetUserInfo() {
        // emptying text fields, image and hiding/showing buttons
        mDisplayName!!.text = ""
        mExternalIdView!!.text = ""
        mAvatarImageView!!.setImageResource(R.drawable.bitmoji450x450)
        mDisplayName!!.visibility = View.INVISIBLE
        mExternalIdView!!.visibility = View.INVISIBLE
        mAvatarImageView!!.visibility = View.INVISIBLE
        mLabelMyProfile!!.visibility = View.VISIBLE
        mSignOutButton!!.visibility = View.INVISIBLE
        mLoginButton!!.visibility = View.VISIBLE
        mLabelDisplayName!!.visibility = View.INVISIBLE
        mLabelExternalId!!.visibility = View.INVISIBLE
    }

    private fun signOutUser() {
        Log.d("SnapkitLogin", "The user is unlinking their profile")
        SnapLogin.getAuthTokenManager(this).clearToken()
    }
}