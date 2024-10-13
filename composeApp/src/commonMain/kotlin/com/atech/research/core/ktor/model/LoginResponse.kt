package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class LoginResponse(
    val uid: String,
    val email: String,
    val name: String,
    val photoUrl: String? = null,
    val userType: String,
)