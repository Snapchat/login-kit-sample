package com.waseem.snaploginkit

enum class AuthState {
    LOGGED_IN,
    LOGGED_OUT
}

data class SnapUser(
    val displayName: String,
    val avatarUrl: String?,
)

data class MainUiState (
    val authState: AuthState,
    val snapUser: SnapUser? = null,
)