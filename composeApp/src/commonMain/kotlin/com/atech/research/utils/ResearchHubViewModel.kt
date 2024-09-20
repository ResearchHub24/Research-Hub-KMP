package com.atech.research.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

expect fun getCoroutineScope(): CoroutineScope

abstract class ResearchHubViewModel : ViewModel() {
    private val viewModelScope = getCoroutineScope()
    protected val scope get() = viewModelScope

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}