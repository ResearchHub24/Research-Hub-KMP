package com.atech.research.ui.compose.main.login.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.atech.research.common.EditText
import com.atech.research.common.GoogleButton
import com.atech.research.common.MainContainer
import com.atech.research.common.PasswordEditTextCompose
import com.atech.research.ui.theme.captionColor
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.isAndroid
import org.jetbrains.compose.resources.painterResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    isAndroid: Boolean = isAndroid()
) {
    MainContainer(
        appBarColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
            ) {
                Image(
                    painterResource(Res.drawable.app_logo),
                    contentDescription = "Logo",
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isAndroid) {
                    GoogleButton { }
                } else {
                    EditText(
                        modifier = Modifier.fillMaxWidth(.5f),
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Email",
                    )
                    PasswordEditTextCompose(
                        modifier = Modifier.fillMaxWidth(.5f),
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Password",
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    GoogleButton (
                        text = "Login",
                        icon = Icons.Outlined.AccountCircle
                    ){ }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    Text(
                        text = "Warning: You need to log in from a mobile device and set up your account before logging in on PC.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.captionColor,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}