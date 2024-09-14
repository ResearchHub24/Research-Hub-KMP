package com.atech.research.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
actual fun isDark(): Boolean = isSystemInDarkTheme()