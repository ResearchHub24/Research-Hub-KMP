package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * Success response
 * This is a success response that is used to store the success data for the response.
 * Used when api does not return any data but only a message
 * @property message The message
 * @constructor Create empty Success response
 * @see ErrorResponse
 */
@Keep
@Serializable
data class SuccessResponse(val message: String)

/**
 * Error response
 * This is a error response that is used to store the error data for the response.
 * Used when api returns an error
 * @property error The error
 * @constructor Create empty Error response
 * @see SuccessResponse
 */
@Keep
@Serializable
data class ErrorResponse(val error: String)
