package com.atech.research.core.notification

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class NotificationModel(
    val message: Message
)

@Keep
@Serializable
data class Message(
    val topic: String? = null,
    val to: String? = null,
    val notification: Notification,
    val data: Data
)

@Keep
@Serializable
data class Notification(
    val title: String, val body: String
)

@Keep
@Serializable
data class Data(
    val key: String,
    val created: String,
    val image: String? = null
)