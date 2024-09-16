package com.atech.research.ui.compose.main.login.compose.util

import android.content.Intent
import android.content.IntentSender
import com.atech.research.BuildConfig
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.researchHubLog
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

@SuppressWarnings("DEPRECATION")
@Suppress("DEPRECATION")
class GoogleAuthUiClient(
    private val oneTapClient: SignInClient
) {
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            if (e is CancellationException) throw e else null
        }
        return result?.pendingIntent?.intentSender
    }


    fun signInWithIntent(data: Intent): Pair<String?, Exception?> = try {
        val credential = oneTapClient.getSignInCredentialFromIntent(data)
        val googleIdToken = credential.googleIdToken
        googleIdToken?.let {
            return it to null
        }
        Pair(null, Exception("No Google ID token"))
    } catch (e: Exception) {
        Pair(null, e)
    }

    suspend fun signOut(
        action: () -> Unit
    ) {
        try {
            oneTapClient.signOut().await()
            action()
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "signOut: $e")
        }
    }


    private fun buildSignInRequest(): com.google.android.gms.auth.api.identity.BeginSignInRequest {
        val webClient = BuildConfig.firebaseWebClient
        return com.google.android.gms.auth.api.identity.BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false).setServerClientId(webClient).build()
            ).setAutoSelectEnabled(true).build()
    }
}