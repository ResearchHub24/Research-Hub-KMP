package com.atech.research.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

/**
 * Get screen width
 * @return [Dp]
 */
@Composable
expect fun getScreenWidth(): Dp

/**
 * Get screen height
 * @return [Dp]
 */
@Composable
expect fun getScreenHeight(): Dp