package com.atech.research.ui.compose.main.login.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import com.atech.research.common.GoogleButton
import com.atech.research.utils.koinViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
actual fun <T : ViewModel> LoginScreenType(
    viewModel: T,
    onEvent: (LogInEvents) -> Unit
) {
    GoogleButton { }
}

class LogInViewModelImp(
    private val auth: FirebaseAuth
) : LogInViewModel() {
    override fun onLoginEvent(event: LogInEvents) {
        when (event) {
            is LogInEvents.OnSignInResult -> println()
            LogInEvents.OnSkipClick -> println()
            is LogInEvents.TriggerAuth -> println()
        }
    }
}

@Composable
actual fun getViewModel(): LogInViewModel =koinViewModel<LogInViewModelImp>()