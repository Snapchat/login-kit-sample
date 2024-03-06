package com.waseem.snaploginkit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waseem.snaploginkit.ui.theme.SnapLoginKitTheme

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(color = Color(0xFFF8F8F3)) //0xFFF8F8F3
                    .padding(bottom = 20.dp)
            ) {
                Text(
                    text = "Scl.",
                    modifier = Modifier.align(Alignment.BottomCenter),
                    style = TextStyle(
                        fontSize = 96.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Cursive,
                        color = MaterialTheme.colorScheme.primary,
                    )
                )
            }

            Text(
                text = stringResource(R.string.slogan),
                modifier = Modifier.padding(vertical = 20.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )

            Text(
                text = stringResource(R.string.login_description),
                modifier = Modifier.padding(vertical = 20.dp),
                textAlign = TextAlign.Center
            )

            Box(modifier = Modifier.height(20.dp))

            SnapchatLoginButton(onLogin = onLogin)

            Row(
                modifier = Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Read snapkit docs")
                }
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp)
                )
            }

        }
    }
}

@Composable
private fun SnapchatLoginButton(onLogin: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors( // Customize button colors if needed
            containerColor = Color(0xFFFFFC03),
            contentColor = Color.Black
        ),
        onClick = onLogin,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_snapchat),
            contentDescription = null,
            modifier = Modifier.padding(end = 10.dp)
        )
        Text(text = "Login with Snapchat")
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    SnapLoginKitTheme {
        LoginScreen {}
    }
}