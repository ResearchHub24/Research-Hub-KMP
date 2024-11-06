package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * Login response
 * This is a login response that is used to store the login data
 * @property uid The user id
 * @property email The user email
 * @property name The user name
 * @property photoUrl The user photo url
 * @property userType The user type
 * @constructor Create empty Login response
 */
@Keep
@Serializable
data class LoginResponse(
    val uid: String,
    val email: String,
    val name: String,
    val photoUrl: String? = null,
    val userType: String,
)