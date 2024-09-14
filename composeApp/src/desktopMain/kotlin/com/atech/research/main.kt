package com.atech.research

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.CloseFullscreen
import androidx.compose.material.icons.outlined.Minimize
import androidx.compose.material.icons.outlined.OpenInFull
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowDecoration
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.atech.research.module.KoinInitializer
import com.atech.research.ui.theme.ResearchHubTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.stopKoin
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo
import java.awt.Dimension
import java.awt.Toolkit

fun main() = application {
    var isKoinInitialized by remember { mutableStateOf(false) }

    if (!isKoinInitialized) {
        try {
            KoinInitializer().init()
            isKoinInitialized = true
        } catch (e: Exception) {
            println("Koin initialization error: ${e.message}")
        }
    }

    val screenSize = Toolkit.getDefaultToolkit().screenSize
    var isMaximized by remember { mutableStateOf(false) }
    var isMinimizing by remember { mutableStateOf(false) }
    var isSlidingUp by remember { mutableStateOf(true) } // Controls slide up/down state

    val windowState = rememberWindowState(
        placement = if (isMaximized) WindowPlacement.Maximized else WindowPlacement.Floating,
        size = calculateWindowSize(screenSize)
    )

    // Vertical slide animation
    val slideOffset: Dp by animateDpAsState(
        targetValue = if (isSlidingUp) 0.dp else windowState.size.height / 2, // slide up/down logic
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val scale by animateFloatAsState(
        targetValue = if (isMinimizing) 0f else 1f,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        finishedListener = { if (isMinimizing) windowState.isMinimized = true }
    )

    val coroutineScope = rememberCoroutineScope()

    Window(
        state = windowState,
        onCloseRequest = {
            if (isKoinInitialized) {
                stopKoin()
            }
            exitApplication()
        },
        title = "Research Hub",
        icon = painterResource(Res.drawable.app_logo),
        decoration = WindowDecoration.Undecorated(),
        transparent = true
    ) {
        Box(
            modifier = Modifier
                .offset(y = slideOffset) // Apply slide offset here
                .scale(scale)
        ) {
            Column {
                AppWindowTitleBar(
                    title = "Research Hub",
                    icon = painterResource(Res.drawable.app_logo),
                    onMinimize = {
                        coroutineScope.launch {
                            isSlidingUp = false // Slide down before minimizing
                            isMinimizing = true
                        }
                    },
                    onMaximize = {
                        isMaximized = !isMaximized
                        windowState.placement = if (isMaximized) WindowPlacement.Maximized else WindowPlacement.Floating
                    },
                    onClose = {
                        if (isKoinInitialized) {
                            stopKoin()
                        }
                        exitApplication()
                    },
                    isMaximized = isMaximized
                )
                App()
            }
        }
    }

    LaunchedEffect(windowState.isMinimized) {
        if (!windowState.isMinimized) {
            isMinimizing = false
            isSlidingUp = true // Slide up when restored
        }
    }
}

@Composable
private fun WindowScope.AppWindowTitleBar(
    title: String = "Research Hub",
    icon: Painter? = null,
    onMinimize: () -> Unit = {},
    onMaximize: () -> Unit = {},
    onClose: () -> Unit = {},
    isMaximized: Boolean = false
) = WindowDraggableArea {
    ResearchHubTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    icon?.let {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TitleBarButton(
                        onClick = onMinimize,
                        icon = Icons.Outlined.Minimize,
                        contentDescription = "Minimize"
                    )
                    TitleBarButton(
                        onClick = onMaximize,
                        icon = if (isMaximized) Icons.Outlined.CloseFullscreen else Icons.Outlined.OpenInFull,
                        contentDescription = if (isMaximized) "Restore" else "Maximize"
                    )
                    TitleBarButton(
                        onClick = onClose,
                        icon = Icons.Outlined.Close,
                        contentDescription = "Close",
                        colors = iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface,
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun TitleBarButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    colors: IconButtonColors = iconButtonColors(
        contentColor = MaterialTheme.colorScheme.onSurface,
    )
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(36.dp),
        colors = colors
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(16.dp)
        )
    }
}


fun calculateWindowSize(screenSize: Dimension): DpSize {
    val preferredWidth = 1000.dp
    val preferredHeight = 800.dp
    val minWidth = 800.dp
    val minHeight = 600.dp
    val maxWidthRatio = 0.8f
    val maxHeightRatio = 0.8f

    val screenWidth = screenSize.width.dp
    val screenHeight = screenSize.height.dp

    val width = preferredWidth.coerceIn(minWidth, screenWidth * maxWidthRatio)
    val height = preferredHeight.coerceIn(minHeight, screenHeight * maxHeightRatio)

    return DpSize(width, height)
}