package com.snap.androidloginkitdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.core.controller.LoginStateController;
import com.snapchat.kit.sdk.login.models.MeData;
import com.snapchat.kit.sdk.login.models.UserDataResponse;
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback;

public class MainActivity extends AppCompatActivity {

    private View mContentView;
    private View mLoginButton;
    private LoginStateController.OnLoginStateChangedListener mLoginStateChangedListener;
    private TextView mDisplayName;
    private TextView mExternalIdView;
    private MeData meData;
    private ImageView mAvatarImageView;
    private Button mSignOutButton;
    private View mLowerLayout;
    private TextView mLabelMyProfile;
    private TextView mLabelDisplayName;
    private TextView mLabelExternalId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // we define the various different visuals we will be using to display information about the user
        mContentView = findViewById(R.id.contentView);
        mLowerLayout = findViewById(R.id.lowerLayout);
        mLoginButton = SnapLogin.getButton(this, (ViewGroup)mLowerLayout);
        mDisplayName = findViewById(R.id.displayNameView);
        mExternalIdView = findViewById(R.id.externalIDView);
        mAvatarImageView = findViewById(R.id.avatarImageView);
        mSignOutButton = findViewById(R.id.signOutButton);
        mLabelMyProfile = findViewById(R.id.labelMyProfile);
        mLabelDisplayName = findViewById(R.id.labelDisplayNameView);
        mLabelExternalId  = findViewById(R.id.labelExternalIdView);

        // the sign out button will allow the user to unlink their profile from the app
        mSignOutButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                signOutUser();
            }
        });

        // the LoginStateListener tells us when a user has signed in/out
        mLoginStateChangedListener =
                new LoginStateController.OnLoginStateChangedListener() {
                    @Override
                    public void onLoginSucceeded() {
                        Log.d("SnapkitLogin", "Login was successful");
                        mSignOutButton.setVisibility(View.VISIBLE);
                        mDisplayName.setVisibility(View.VISIBLE);
                        mExternalIdView.setVisibility(View.VISIBLE);
                        mAvatarImageView.setVisibility(View.VISIBLE);
                        mLabelMyProfile.setVisibility(View.INVISIBLE);
                        mLoginButton.setVisibility(View.INVISIBLE);
                        mLabelDisplayName.setVisibility(View.VISIBLE);
                        mLabelExternalId.setVisibility(View.VISIBLE);
                        getUserDetails();
                    }

                    @Override
                    public void onLoginFailed() {
                        Log.d("SnapkitLogin", "Login was unsuccessful");
                        mDisplayName.setText(R.string.not_logged_in);
                    }

                    @Override
                    public void onLogout() {
                        // when the user unlinks their account we reset the fields and make the login button visible
                        Log.d("SnapkitLogin", "User logged out");
                        resetUserInfo();
                    }
                };

        SnapLogin.getLoginStateController(this).addOnLoginStateChangedListener(mLoginStateChangedListener);
    }

    private void getUserDetails() {
        boolean isUserLoggedIn = SnapLogin.isUserLoggedIn(this);
        if(isUserLoggedIn) {
            Log.d("SnapkitLogin", "The user is logged in");

            // set a list of the data the app wants to use - these need to mirror the snap_connect_scopes set in arrays.xml
            String query = "{me{bitmoji{avatar},displayName,externalId}}";

            SnapLogin.fetchUserData(this, query, null, new FetchUserDataCallback() {
                @Override
                public void onSuccess(@Nullable UserDataResponse userDataResponse) {
                    if (userDataResponse == null || userDataResponse.getData() == null) {
                        return;
                    }

                    meData = userDataResponse.getData().getMe();
                    if (meData == null) {
                        return;
                    }

                    // set the value of the display name
                    mDisplayName.setText(userDataResponse.getData().getMe().getDisplayName());

                    // set the value of the external id
                    mExternalIdView.setText(userDataResponse.getData().getMe().getExternalId());

                    // not all users have a bitmoji connected, if the account has bitmoji connected we load the bitmoji avatar
                    if (meData.getBitmojiData() != null) {
                        Glide.with(getBaseContext()).load(meData.getBitmojiData().getAvatar()).into(mAvatarImageView);
                    }
                }

                @Override
                public void onFailure(boolean isNetworkError, int statusCode) {
                    Log.d("SnapkitLogin", "No user data fetched " + statusCode );
                }
            });

        }
    }

    private void resetUserInfo() {
        // emptying text fields, image and hiding/showing buttons
        mDisplayName.setText("");
        mExternalIdView.setText("");
        mAvatarImageView.setImageResource(R.drawable.bitmoji450x450);
        mDisplayName.setVisibility(View.INVISIBLE);
        mExternalIdView.setVisibility(View.INVISIBLE);
        mAvatarImageView.setVisibility(View.INVISIBLE);
        mLabelMyProfile.setVisibility(View.VISIBLE);
        mSignOutButton.setVisibility(View.INVISIBLE);
        mLoginButton.setVisibility(View.VISIBLE);
        mLabelDisplayName.setVisibility(View.INVISIBLE);
        mLabelExternalId.setVisibility(View.INVISIBLE);
    }

    private void signOutUser() {
        Log.d("SnapkitLogin", "The user is unlinking their profile");
        SnapLogin.getAuthTokenManager(this).clearToken();
    }

}
