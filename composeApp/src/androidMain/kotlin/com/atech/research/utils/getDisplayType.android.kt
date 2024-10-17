package com.atech.research.utils

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
actual fun getDisplayType(): DeviceType {
    val configuration = LocalConfiguration.current
    val windowWidthSizeClass = when {
        configuration.screenWidthDp < 600 -> WindowWidthSizeClass.Compact
        configuration.screenWidthDp < 840 -> WindowWidthSizeClass.Medium
        else -> WindowWidthSizeClass.Expanded
    }
    return when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> DeviceType.MOBILE
        WindowWidthSizeClass.Medium -> DeviceType.TABLET
        else -> DeviceType.DESKTOP
    }
}