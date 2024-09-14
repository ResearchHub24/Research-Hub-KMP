package com.atech.research.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 32.dp,
    val bottomPadding: Dp = 56.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
