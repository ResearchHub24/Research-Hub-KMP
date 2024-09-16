package com.atech.research.core.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SuccessResponse(val message: String)