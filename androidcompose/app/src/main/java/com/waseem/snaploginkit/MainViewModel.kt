package com.waseem.snaploginkit

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.snap.loginkit.BitmojiQuery
import com.snap.loginkit.LoginResultCallback
import com.snap.loginkit.SnapLogin
import com.snap.loginkit.UserDataQuery
import com.snap.loginkit.UserDataResultCallback
import com.snap.loginkit.exceptions.LoginException
import com.snap.loginkit.exceptions.UserDataException
import com.snap.loginkit.models.UserDataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val snapLogin: SnapLogin
) : ViewModel() {

    private val _uiState = snapLogin.isUserLoggedIn.let {
        if (it) {
            mutableStateOf(MainUiState(AuthState.LOGGED_IN))
        } else {
            mutableStateOf(MainUiState(AuthState.LOGGED_OUT))
        }
    }
    val uiState: State<MainUiState> = _uiState

    fun onLogin() {
        snapLogin.startTokenGrant(
            object : LoginResultCallback {
                override fun onStart() {
                    Log.d("SnapLoginKit", "onStart")
                }

                override fun onSuccess(p0: String) {
                    _uiState.value = _uiState.value.copy(
                        authState = AuthState.LOGGED_IN
                    )
                }

                override fun onFailure(p0: LoginException) {
                    Log.d("SnapLoginKit", "onFailure")
                }

            }
        )
    }

    fun getUserInfo() {
        val bitmojiQuery = BitmojiQuery.newBuilder().withAvatarId().withTwoDAvatarUrl().build()
        val userDetailQuery = UserDataQuery.newBuilder()
            .withDisplayName()
            .withBitmoji(bitmojiQuery)
            .build()
        snapLogin.fetchUserData(userDetailQuery, object : UserDataResultCallback {
            override fun onSuccess(userDetailResult: UserDataResult) {
                userDetailResult.data?.meData?.let {
                    val displayName = it.displayName ?: "No name"
                    val avatarUrl = it.bitmojiData?.twoDAvatarUrl

                    _uiState.value = _uiState.value.copy(
                        snapUser = SnapUser(
                            displayName = displayName,
                            avatarUrl = avatarUrl
                        )
                    )

                    Log.d("SnapLoginKit", "displayName: $displayName")
                    Log.d("SnapLoginKit", "avatarUrl: $avatarUrl")
                }
                TODO("Not yet implemented")
            }

            override fun onFailure(p0: UserDataException) {
                TODO("Not yet implemented")
            }

        })
    }

    fun onLogout() {
        snapLogin.clearToken()
        _uiState.value = _uiState.value.copy(
            authState = AuthState.LOGGED_OUT
        )
    }
}