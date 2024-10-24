package com.atech.research.ui.compose.login.compose.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.atech.research.core.ktor.model.LoginResponse
import com.atech.research.utils.DataState

data class LogInState(
    val isNewUser: Boolean = false,
    val token: String? = null,
    val errorMessage: String? = null,
    val uId: String? = null,
    val email: String? = null,
    val password: String? = null,
    val userName: String? = null,
    val photoUrl: String? = null,
    val userType: String? = null
)


sealed interface LogInEvents {
    data class OnSignInResult(val state: LogInState) : LogInEvents
    data class TriggerAuth(val token: String) : LogInEvents
    data object OnSkipClick : LogInEvents
    data object PreformLogOutOnError : LogInEvents
    data class LogIn(
        val email: String,
        val password: String,
        val action: (DataState<LoginResponse>) -> Unit
    ) : LogInEvents
}

abstract class LogInViewModel : ViewModel() {
    abstract fun onLoginEvent(event: LogInEvents)

    abstract var logInState: State<LogInState>
}

@Composable
expect fun LoginScreenType(
    viewModel: LogInViewModel,
    onEvent: (LogInEvents) -> Unit,
    onLogInDone: (LoginResponse) -> Unit
)

@Composable
expect fun getViewModel(): LogInViewModel


