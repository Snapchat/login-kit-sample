package com.waseem.snaploginkit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.snap.loginkit.LoginResultCallback
import com.snap.loginkit.SnapLogin
import com.snap.loginkit.SnapLoginProvider
import com.snap.loginkit.exceptions.LoginException
import com.waseem.snaploginkit.ui.theme.SnapLoginKitTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            SnapLoginKitTheme {
                // A surface container using the 'background' color from the theme
                Scaffold {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val snapLogin: SnapLogin = SnapLoginProvider.get(baseContext)

                        Text(text = "")

                        Button(onClick = {
                            snapLogin.startTokenGrant()
                            snapLogin.clearToken()
                        }) {
                            Text(text = "Logout")
                        }

                        SnapchatLoginButton(snapLogin = snapLogin)
                    }
                }
            }
        }
    }
}

@Composable
fun SnapchatLoginButton(snapLogin: SnapLogin) {
    Button(onClick = {
        snapLogin.startTokenGrant(
            object : LoginResultCallback {
                override fun onStart() {
                    Log.d("SnapLoginKit", "onStart")
                }

                override fun onSuccess(p0: String) {
                    Log.d("SnapLoginKit", "onSuccess")
                }

                override fun onFailure(p0: LoginException) {
                    Log.d("SnapLoginKit", "onFailure")
                }

            }
        )
    }) {
        Text(text = "Login with Snapchat")
    }
}

@Composable
fun SnapProfile(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SnapLoginKitTheme {
        SnapProfile("Android")
    }
}