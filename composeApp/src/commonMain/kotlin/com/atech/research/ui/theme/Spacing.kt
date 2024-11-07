package com.atech.research.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Spacing
 *
 * @property default Dp
 * @property extraSmall Dp
 * @property small Dp
 * @property medium Dp
 * @property large Dp
 * @property extraLarge Dp
 * @property bottomPadding Dp
 * @constructor Create empty Spacing
 */
data class Spacing(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 16.dp,
    val extraLarge: Dp = 32.dp,
    val bottomPadding: Dp = 56.dp
)

/**
 * Local spacing
 */
val LocalSpacing = compositionLocalOf { Spacing() }

/**
 * Spacing
 *
 * @return Spacing
 */
val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
