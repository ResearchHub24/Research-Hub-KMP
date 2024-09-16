package com.atech.research.ui.compose.login.compose.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
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
    data object PreformLogOutOnError : LogInEvents

}

abstract class LogInViewModel : ViewModel() {
    abstract fun onLoginEvent(event: LogInEvents)

    abstract var logInState: State<LogInState>
}

@Composable
expect fun LoginScreenType(
    viewModel: LogInViewModel,
    onEvent: (LogInEvents) -> Unit,
    onLogInDone : (String) -> Unit
)

@Composable
expect fun getViewModel(): LogInViewModel

