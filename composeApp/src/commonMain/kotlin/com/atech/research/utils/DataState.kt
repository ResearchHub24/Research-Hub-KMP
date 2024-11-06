package com.atech.research.utils

/**
 * Data state
 * This class is used to represent the state of the data
 * @param T The type of the data
 */
sealed class DataState<out T> {
    /**
     * Success
     * This class is used to represent the success state of the data
     * @param T The type of the data
     * @property data The data
     * @constructor Create empty Success
     */
    data class Success<T>(val data: T) : DataState<T>()

    /**
     * Error
     * This class is used to represent the error state of the data
     * @property exception The exception
     * @constructor Create empty Error
     * @param exception The exception
     */
    data class Error(val exception: Exception) : DataState<Nothing>()

    /**
     * Loading
     * This class is used to represent the loading state of the data
     */
    data object Loading : DataState<Nothing>()
}