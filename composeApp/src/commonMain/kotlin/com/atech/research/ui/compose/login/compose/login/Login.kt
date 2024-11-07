package com.atech.research.ui.compose.login.compose.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.atech.research.core.ktor.model.LoginResponse
import com.atech.research.utils.DataState

/**
 * Log in state
 * This is the state of the log in.
 * @param isNewUser The flag to indicate if the user is new
 * @param token The token of the user
 * @param errorMessage The error message
 * @param uId The user id
 * @param email The email of the user
 * @param password The password of the user
 * @param userName The user name
 * @param photoUrl The photo url
 * @param userType The user type
 */
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

/**
 * Log in events
 * This is the sealed interface that represents the events in the log in.
 */
sealed interface LogInEvents {
    /**
     * On sign in result event
     * This event is called when the sign in result is received.
     * @param state The state of the log in
     */
    data class OnSignInResult(val state: LogInState) : LogInEvents

    /**
     * Trigger auth event
     * This event is called when the auth is triggered.
     * @param token The token
     */
    data class TriggerAuth(val token: String) : LogInEvents

    /**
     * On sign up click event
     * This event is called when the sign up is clicked.
     */
    data object OnSkipClick : LogInEvents

    /**
     * On sign up click event
     * This event is called when the sign up is clicked.
     */
    data object PreformLogOutOnError : LogInEvents

    /**
     * Log in event
     * This event is called when the log in is triggered.
     * @param email The email
     * @param password The password
     * @param action The action to be performed
     */
    data class LogIn(
        val email: String,
        val password: String,
        val action: (DataState<LoginResponse>) -> Unit
    ) : LogInEvents
}

/**
 * Log in view model
 * This is the view model for the log in.
 * @see LogInEvents
 */
abstract class LogInViewModel : ViewModel() {
    /**
     * On login event
     * This is the callback to be called when the login event is triggered.
     * @param event The event to be triggered
     */
    abstract fun onLoginEvent(event: LogInEvents)

    /**
     * Log in state
     * This is the state of the log in.
     */
    abstract var logInState: State<LogInState>
}

/**
 * Log in screen type
 * This is the composable function that represents the log in screen.
 * Platform specific implementation should be provided.
 * @param viewModel The view model
 * @param onEvent The callback to be called when an event is triggered
 * @param onLogInDone The callback to be called when the log in is done
 */
@Composable
expect fun LoginScreenType(
    viewModel: LogInViewModel,
    onEvent: (LogInEvents) -> Unit,
    onLogInDone: (LoginResponse) -> Unit
)

/**
 * Get view model
 * This is the function that returns the view model.
 * Platform specific implementation should be provided.
 */
@Composable
expect fun getViewModel(): LogInViewModel


