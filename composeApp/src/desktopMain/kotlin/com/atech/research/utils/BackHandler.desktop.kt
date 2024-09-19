package com.atech.research.utils

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
//    androidx.activity.compose.BackHandler(enabled) { onBack() }
}