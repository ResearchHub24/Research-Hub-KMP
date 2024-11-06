package com.atech.research.core.notification

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * Notification model
 * This is a data class that represents the notification model
 * @property message Message
 * @constructor Create empty Notification model
 * @see Message
 */
@Keep
@Serializable
data class NotificationModel(
    val message: Message
)

/**
 * Message
 * This is a data class that represents the message model
 * @property topic Topic
 * @property to To
 * @property notification Notification
 * @property data Data
 * @constructor Create empty Message
 * @see Notification
 */
@Keep
@Serializable
data class Message(
    val topic: String? = null,
    val to: String? = null,
    val notification: Notification,
    val data: Data
)

/**
 * Notification
 * This is a data class that represents the notification model
 * @property title Title
 * @property body Body
 * @constructor Create empty Notification
 * @see Data
 */
@Keep
@Serializable
data class Notification(
    val title: String, val body: String
)

/**
 * Data
 * This is a data class that represents the data model
 * @property key Key
 * @property created Created
 * @property image Image
 * @constructor Create empty Data
 * @see Notification
 * @see Message
 * @see NotificationModel
 */
@Keep
@Serializable
data class Data(
    val key: String,
    val created: String,
    val image: String? = null
)