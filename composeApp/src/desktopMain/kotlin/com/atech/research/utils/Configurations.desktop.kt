package com.atech.research.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.awt.Toolkit

@Composable
actual fun getScreenWidth(): Dp {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val density = LocalDensity.current
    return with(density) { screenSize.width.toDp() }
}


@Composable
actual fun getScreenHeight(): Dp {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val density = LocalDensity.current
    return with(density) { screenSize.height.toDp() }
}