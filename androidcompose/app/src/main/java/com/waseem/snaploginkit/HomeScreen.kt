package com.waseem.snaploginkit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExitToApp
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.waseem.snaploginkit.ui.theme.SnapLoginKitTheme

@Composable
fun HomeScreen(
    snapUser: SnapUser?,
    onLogout: () -> Unit
) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(color = Color(0xFFF8F8F3)) //0xFFF8F8F3
                    .padding(bottom = 30.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                AsyncImage(
                    model = snapUser?.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = CircleShape)
                        .border(width = 3.dp, color = MaterialTheme.colorScheme.secondary, shape = CircleShape)
                )
            }

            Text(
                text = "Hello ${snapUser?.displayName},",
                modifier = Modifier.padding(top = 20.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = stringResource(R.string.home_description),
                modifier = Modifier.padding(vertical = 20.dp),
            )

            Box(modifier = Modifier.height(20.dp))

            Divider()

            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color.Red
                ),
                contentPadding = PaddingValues(horizontal = 0.dp),
                onClick = onLogout
            ) {
                Icon(Icons.Rounded.ExitToApp, contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(R.string.logout)
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
        HomeScreen(
            snapUser = SnapUser(displayName = "Waseem Abbas", avatarUrl = "https://i.pravatar.cc/300")
        ) {}
    }
}