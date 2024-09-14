package com.atech.research

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowDecoration
import androidx.compose.ui.window.application
import com.atech.research.module.KoinInitializer
import org.jetbrains.compose.resources.painterResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo
import researchhub.composeapp.generated.resources.compose_multiplatform

fun main() = application {
    KoinInitializer().init()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Research Hub",
        icon = painterResource(Res.drawable.app_logo),
    ) {
        App()
    }
}