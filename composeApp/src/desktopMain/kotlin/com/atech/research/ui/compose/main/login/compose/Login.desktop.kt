package com.atech.research.ui.compose.main.login.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.atech.research.common.EditText
import com.atech.research.common.GoogleButton
import com.atech.research.common.PasswordEditTextCompose
import com.atech.research.ui.theme.captionColor
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.koinViewModel

@Composable
actual fun LoginScreenType(
    viewModel: LogInViewModel,
    onEvent: (LogInEvents) -> Unit,
    onLogInDone : () -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
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
    GoogleButton(
        text = "Login",
        icon = Icons.Outlined.AccountCircle
    ) { }
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
    Text(
        text = "Warning: You need to log in from a mobile device and set up your account before logging in on PC.",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.captionColor,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}


internal class LogInViewModelImp : LogInViewModel() {
    override var logInState: State<LogInState> = mutableStateOf(LogInState())
    override fun onLoginEvent(event: LogInEvents) {
        when (event) {
            is LogInEvents.OnSignInResult -> println()
            LogInEvents.OnSkipClick -> println()
            is LogInEvents.TriggerAuth -> println()
            LogInEvents.PreformLogOutOnError -> println()
        }
    }
}

@Composable
actual fun getViewModel(): LogInViewModel = koinViewModel<LogInViewModelImp>()