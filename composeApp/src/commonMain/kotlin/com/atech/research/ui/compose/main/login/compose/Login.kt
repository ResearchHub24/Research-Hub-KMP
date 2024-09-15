package com.atech.research.ui.compose.main.login.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel

data class LogInState(
    val isNewUser: Boolean = false,
    val token: String? = null,
    val errorMessage: String? = null,
    val uId: String? = null,
    val email: String? = null,
    val password: String? = null
)


sealed interface LogInEvents {
    data class OnSignInResult(val state: LogInState) : LogInEvents
    data class TriggerAuth(val token: String) : LogInEvents
    data object OnSkipClick : LogInEvents
}

abstract class LogInViewModel : ViewModel() {
    abstract fun onLoginEvent(event: LogInEvents)
}

@Composable
expect fun <T : ViewModel> LoginScreenType(
    viewModel: T,
    onEvent: (LogInEvents) -> Unit
)

@Composable
expect fun getViewModel(): LogInViewModel

