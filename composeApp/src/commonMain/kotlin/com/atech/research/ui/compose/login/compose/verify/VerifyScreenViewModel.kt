package com.atech.research.ui.compose.login.compose.verify

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.usecase.IsUserVerifiedUseCase
import com.atech.research.utils.DataState
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch

/**
 * Verify screen view model
 * View model for verify screen
 * @param isUserVerifiedUseCase IsUserVerifiedUseCase
 * @param pref PrefManager
 * @constructor Create empty Verify screen view model
 * @see IsUserVerifiedUseCase
 * @see PrefManager
 */
class VerifyScreenViewModel(
    private val isUserVerifiedUseCase: IsUserVerifiedUseCase,
    private val pref: PrefManager
) : ResearchHubViewModel() {
    private val _isVerified = mutableStateOf<DataState<Boolean>>(DataState.Loading)
    val isVerified: State<DataState<Boolean>> get() = _isVerified

    init {
        loadIsVerified()
    }

    private fun loadIsVerified() = scope.launch {
        _isVerified.value = isUserVerifiedUseCase.invoke(
            pref.getString(Prefs.USER_ID.name)
        )
    }

}