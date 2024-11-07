package com.atech.research.ui.compose.profile

/**
 * Sign out helper
 * This is the sign out helper.
 * Platform specific implementation is required.
 * @constructor Create empty Sign out helper
 */
expect class SignOutHelper {
    /**
     * Sign out
     * This is the sign out suspend function.
     * @param action Function1<Exception?, Unit>
     */
    suspend fun signOut(action: (Exception?) -> Unit)
}