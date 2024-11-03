package com.atech.research.utils

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


class CloseAppImp(
    val context: Activity
) : CloseApp {
    override fun closeApp() {
        context.finishAffinity()
    }

}

@Composable
actual fun closeApp(): CloseApp {
    val activity = LocalContext.current as Activity
    return CloseAppImp(activity)
}
