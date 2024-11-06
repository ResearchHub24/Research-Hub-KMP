package com.atech.research.utils

/**
 * Toast
 * This class is implemented in Android and Desktop
 * @constructor Create empty Toast
 */
expect class Toast {
    /**
     * Show
     * This function is used to show a toast message
     * @param message
     * @param duration
     */
    fun show(message: String, duration: ToastDuration)
}

/**
 * Toast duration
 * This enum class is used to define the duration of a toast message
 */
enum class ToastDuration {
    SHORT,
    LONG
}