package com.waseem.snaploginkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.waseem.snaploginkit.ui.theme.SnapLoginKitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SnapLoginKitTheme {
                // A surface container using the 'background' color from the theme

                MainScreenContent(
                    state = viewModel.uiState.value,
                    onLogout = { viewModel.onLogout() },
                    onLogin = { viewModel.onLogin() }
                )
            }
        }
    }
}

@Composable
private fun MainScreenContent(
    state: MainUiState,
    onLogin: () -> Unit,
    onLogout: () -> Unit
) {
    when (state.authState) {
        AuthState.LOGGED_IN -> {
            HomeScreen(
                onLogout = onLogout,
                snapUser = state.snapUser
            )
        }

        AuthState.LOGGED_OUT -> {
            LoginScreen(onLogin = onLogin)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview(
    @PreviewParameter(MainScreenUiStatePreviewParameterProvider::class) state: MainUiState
) {
    SnapLoginKitTheme {
        MainScreenContent(
            state = state,
            onLogin = { },
            onLogout = {}
        )
    }
}

class MainScreenUiStatePreviewParameterProvider : PreviewParameterProvider<MainUiState> {
    override val values = sequenceOf(
        MainUiState(AuthState.LOGGED_OUT),
        MainUiState(AuthState.LOGGED_IN, snapUser = SnapUser("Waseem", "https://i.pravatar.cc/300"))
    )
}