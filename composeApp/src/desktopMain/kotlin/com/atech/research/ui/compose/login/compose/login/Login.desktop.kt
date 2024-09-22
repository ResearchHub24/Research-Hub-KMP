package com.atech.research.ui.compose.login.compose.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.CardDefaults
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
import androidx.lifecycle.viewModelScope
import com.atech.research.common.DisplayCard
import com.atech.research.common.EditText
import com.atech.research.common.GoogleButton
import com.atech.research.common.PasswordEditTextCompose
import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.ui.theme.captionColor
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.DataState
import com.atech.research.utils.koinViewModel
import kotlinx.coroutines.launch

@Composable
actual fun LoginScreenType(
    viewModel: LogInViewModel,
    onEvent: (LogInEvents) -> Unit,
    onLogInDone: (String, String) -> Unit
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var error by rememberSaveable { mutableStateOf("") }
    var logInMessage by rememberSaveable { mutableStateOf("LogIn") }
    var hasClick by rememberSaveable { mutableStateOf(false) }

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
    AnimatedVisibility(visible = email.isNotEmpty() && password.isNotEmpty()) {
        GoogleButton(
            text = logInMessage,
            loadingText = logInMessage,
            hasClick = hasClick,
            hasClickChange = { value ->
                hasClick = !value
            },
            icon = Icons.Outlined.AccountCircle
        ) {
            logInMessage = "Logging In..."
            hasClick = true
            onEvent(LogInEvents.LogIn(email, password, { dataState ->
                when (dataState) {
                    is DataState.Error -> {
                        hasClick = false
                        error = dataState.exception.message ?: "An error occurred"
                    }

                    DataState.Loading -> {
                        logInMessage = "Logging In..."
                        hasClick = true
                    }

                    is DataState.Success -> {
                        hasClick = false
                        onLogInDone(
                            "${dataState.data.uid}$${dataState.data.userType}",
                            dataState.data.displayName ?: ""
                        )
                    }
                }
            }))
        }
    }
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
    AnimatedVisibility(visible = error.isNotEmpty()) {
        DisplayCard(
            modifier = Modifier.fillMaxWidth(.5f),
            border = BorderStroke(
                width = CardDefaults.outlinedCardBorder().width,
                color = MaterialTheme.colorScheme.error
            ),
        ) {
            Text(
                text = error,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
    Text(
        text = "Warning: You need to log in from a mobile device and set up your account before logging in on PC.",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.captionColor,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}


internal class LogInViewModelImp(
    private val client: ResearchHubClient,
) : LogInViewModel() {
    override var logInState: State<LogInState> = mutableStateOf(LogInState())
    override fun onLoginEvent(event: LogInEvents) {
        when (event) {
            is LogInEvents.OnSignInResult -> println()
            LogInEvents.OnSkipClick -> println()
            is LogInEvents.TriggerAuth -> println()
            LogInEvents.PreformLogOutOnError -> println()
            is LogInEvents.LogIn -> viewModelScope.launch {
                val dataState = client.logInUser(event.email, event.password)
                event.action.invoke(dataState)
            }
        }
    }
}

@Composable
actual fun getViewModel(): LogInViewModel = koinViewModel<LogInViewModelImp>()