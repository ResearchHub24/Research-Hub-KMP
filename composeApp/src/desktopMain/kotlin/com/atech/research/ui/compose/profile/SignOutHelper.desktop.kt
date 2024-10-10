package com.atech.research.ui.compose.profile

actual class SignOutHelper {
    actual suspend fun signOut(action: (Exception?) -> Unit) {
        action(null)
    }
}