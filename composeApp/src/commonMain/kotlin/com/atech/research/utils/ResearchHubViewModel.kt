package com.atech.research.utils

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * Get coroutine scope
 * This function is implemented in Android and Desktop
 * @return [CoroutineScope]
 */
expect fun getCoroutineScope(): CoroutineScope

/**
 * ResearchHubViewModel
 * Base class for all view models in the ResearchHub app
 */
abstract class ResearchHubViewModel : ViewModel() {
    /**
     * Scope
     * This property is used to get the coroutine scope
     */
    private val viewModelScope = getCoroutineScope()

    /**
     * Scope
     * This property is used to get the coroutine scope
     */
    protected val scope get() = viewModelScope

    /**
     * onCleared
     * This function is called when the view model is cleared
     */
    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}