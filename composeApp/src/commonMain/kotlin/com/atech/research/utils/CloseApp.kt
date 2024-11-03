package com.atech.research.utils

import androidx.compose.runtime.Composable

interface CloseApp {
    fun closeApp()
}

@Composable
expect fun closeApp() : CloseApp

