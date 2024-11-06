package com.atech.research.utils

import androidx.compose.runtime.Composable

/**
 * Back handler
 * This is a composable that will handle the back press event
 * This is a common composable that will be implemented in the platform specific module
 * @param enabled - if the back handler is enabled
 * @param onBack - the callback that will be called when the back button is pressed
 */
@Composable
expect fun BackHandler(enabled: Boolean = true, onBack: () -> Unit)
