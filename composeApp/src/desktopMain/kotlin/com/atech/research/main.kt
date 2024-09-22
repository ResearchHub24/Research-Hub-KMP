package com.atech.research

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.atech.research.module.KoinInitializer
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.utils.PrefManager
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.context.stopKoin
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo
import java.awt.Dimension
import java.awt.Toolkit
import java.nio.file.Paths

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
    val windowState = rememberWindowState(
        size = calculateWindowSize(screenSize)
    )
    ResearchHubTheme {
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
        ) {
            App(
                pref = koinInject<PrefManager>()
            )
        }
    }
}

internal fun getAppDataPath(): String {
    val userHome = System.getProperty("user.home")
    return when {
        System.getProperty("os.name").contains("Windows", ignoreCase = true) ->
            Paths.get(System.getenv("APPDATA"), "ResearchHub").toString()

        System.getProperty("os.name").contains("Mac", ignoreCase = true) ->
            Paths.get(userHome, "Library", "Application Support", "ResearchHub").toString()

        else -> // Linux and other Unix-like systems
            Paths.get(userHome, ".config", "ResearchHub").toString()
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