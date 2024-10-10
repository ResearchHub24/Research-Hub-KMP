package com.atech.research.ui.compose.profile

import android.content.Context
import com.atech.research.ui.compose.login.compose.util.GoogleAuthUiClient
import com.atech.research.ui.compose.login.compose.util.SignOutUseCase
import com.google.android.gms.auth.api.identity.Identity

actual class SignOutHelper(
    private val context: Context,
    private val signOut: SignOutUseCase
) {
    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    actual suspend fun signOut(action: (Exception?) -> Unit) {
        try {
            googleAuthUiClient.signOut {
                signOut.invoke(action)
            }
        } catch (e: Exception) {
            action(e)
        }
    }
}
