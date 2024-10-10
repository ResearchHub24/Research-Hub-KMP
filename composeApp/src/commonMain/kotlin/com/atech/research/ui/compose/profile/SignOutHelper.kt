package com.atech.research.ui.compose.profile

expect class SignOutHelper {
    suspend fun signOut(action: (Exception?) -> Unit)
}