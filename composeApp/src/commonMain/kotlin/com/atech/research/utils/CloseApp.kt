package com.atech.research.utils

import androidx.compose.runtime.Composable

/**
 * Close app
 * This is a composable that will close the app
 * @constructor Create empty Close app
 */
interface CloseApp {
    /**
     * Close app
     */
    fun closeApp()
}

/**
 * Close app
 * This is a composable that will close the app
 * This is a common composable that will be implemented in the platform specific module
 * @return [CloseApp]
 * @see CloseApp
 */
@Composable
expect fun closeApp(): CloseApp

