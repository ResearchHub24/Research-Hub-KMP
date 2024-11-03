package com.atech.research.utils

import androidx.compose.runtime.Composable
import kotlin.system.exitProcess

@Composable
actual fun closeApp(): CloseApp {
    return object : CloseApp {
        override fun closeApp() {
            exitProcess(0)
        }
    }
}