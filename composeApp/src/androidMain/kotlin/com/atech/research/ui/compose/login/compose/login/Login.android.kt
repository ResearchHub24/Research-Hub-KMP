package com.atech.research.ui.compose.login.compose.login

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.atech.research.common.GoogleButton
import com.atech.research.core.ktor.model.LoginResponse
import com.atech.research.ui.compose.login.compose.util.GoogleAuthUiClient
import com.atech.research.ui.compose.login.compose.util.LogInWithGoogleStudent
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.koinViewModel
import com.atech.research.utils.researchHubLog
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

@Composable
actual fun LoginScreenType(
    viewModel: LogInViewModel,
    onEvent: (LogInEvents) -> Unit,
    onLogInDone: (LoginResponse) -> Unit
) {
    val logInState by viewModel.logInState
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var logInMessage by rememberSaveable { mutableStateOf("Creating Account...") }
    var hasClick by rememberSaveable { mutableStateOf(false) }
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                coroutineScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        data = result.data ?: return@launch
                    )
                    onEvent(
                        LogInEvents.OnSignInResult(
                            LogInState(
                                token = signInResult.first,
                                errorMessage = signInResult.second?.message
                            )
                        )
                    )
                }
            }
        })
    LaunchedEffect(key1 = logInState.errorMessage) {
        logInState.errorMessage?.let { error ->
            hasClick = false
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            googleAuthUiClient.signOut {
                onEvent.invoke(LogInEvents.PreformLogOutOnError)
            }
        }
    }
    LaunchedEffect(key1 = logInState) {
        logInState.token?.let { token ->
            logInMessage = "Signing In... 🔃"
            onEvent(LogInEvents.TriggerAuth(token))
        }
        logInState.uId?.let {
            logInMessage = "Sign Done"
            onLogInDone(
                LoginResponse(
                    uid = logInState.uId ?: "",
                    name = logInState.userName ?: "",
                    email = logInState.email ?: "",
                    photoUrl = logInState.photoUrl ?: "",
                    userType = logInState.userType ?: ""
                )
            )
        }
    }
    GoogleButton(loadingText = logInMessage, hasClick = hasClick, hasClickChange = { value ->
        hasClick = value
    }) {
        hasClick = true
        coroutineScope.launch {
            val sigInIntentSender = googleAuthUiClient.signIn()
            launcher.launch(
                IntentSenderRequest.Builder(
                    sigInIntentSender ?: return@launch
                ).build()
            )
        }
    }
}

class LogInViewModelImp(
    private val logIn: LogInWithGoogleStudent
) : LogInViewModel() {

    private val _logInState = mutableStateOf(LogInState())
    override var logInState: State<LogInState> = _logInState
    private fun logIn(uid: String) = viewModelScope.launch {
        logIn.invoke(
            uid = uid,
        ) { state ->
            when (state) {
                is DataState.Error -> _logInState.value =
                    LogInState(errorMessage = state.exception.message)

                DataState.Loading -> {}
                is DataState.Success -> {
                    _logInState.value =
                        LogInState(
                            uId = state.data.uid,
                            userName = state.data.displayName,
                            photoUrl = state.data.photoUrl,
                            email = state.data.email,
                            userType = state.data.userType
                        )
                }
            }
        }
    }

    override fun onLoginEvent(event: LogInEvents) {
        when (event) {
            is LogInEvents.OnSignInResult -> _logInState.value = event.state
            LogInEvents.OnSkipClick -> researchHubLog(ResearchLogLevel.DEBUG, "Skip Click")
            is LogInEvents.TriggerAuth -> logIn(event.token)
            LogInEvents.PreformLogOutOnError -> println()
            else -> {}
        }
    }
}

@Composable
actual fun getViewModel(): LogInViewModel = koinViewModel<LogInViewModelImp>()