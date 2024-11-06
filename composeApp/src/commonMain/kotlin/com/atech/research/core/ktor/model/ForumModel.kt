package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


/**
 * Forum request
 * Wrapper class for forum model and message model
 * @property forumModel forum model
 * @property message message model
 * @constructor Create empty Forum request
 * @see ForumRequest
 * @see MessageModel
 */
@Serializable
@Keep
data class ForumRequest(
    val forumModel: ForumModel,
    val message: MessageModel? = null
)

/**
 * Get path
 * @return String path of firebase database
 */
fun ForumModel.getPath() =
    "${this.createdChatUid}_${this.createdChatUserName}_${this.receiverChatUid}_${this.receiverChatUserName}"

/**
 * Forum model
 * Base class to create new Forum
 * @property createdChatUid String UID of created user (Faculty)
 * @property createdChatUserName String
 * @property createdChatUserEmail String
 * @property createdChatProfileUrl String
 * @property createdUnreadMessageCount Int
 * @property receiverChatUid String UID of received user (Student)
 * @property receiverChatUserName String
 * @property receiverChatUserEmail String
 * @property receiverChatProfileUrl String
 * @property receiverUnreadMessageCount Int
 * @property created Long
 * @constructor Create empty Forum model
 * @see ForumRequest
 */
@Keep
@Serializable
data class ForumModel(
    val createdChatUid: String,
    val createdChatUserName: String,
    val createdChatUserEmail: String,
    val createdChatProfileUrl: String,
    val createdUnreadMessageCount: Int = 0,

    val receiverChatUid: String,
    val receiverChatUserName: String,
    val receiverChatUserEmail: String,
    val receiverChatProfileUrl: String,
    val receiverUnreadMessageCount: Int = 0,

    val created: Long = System.currentTimeMillis()
)

/**
 * Filter forum model
 * Filter forum model to get chat user details
 * Use in ui to show chat user details
 * @property chatUid String
 * @property chatUserName String
 * @property chatUserEmail String
 * @property chatProfileUrl String
 * @property unreadMessageCount Int
 * @constructor Create empty Filter forum model
 * @see ForumModel
 * @see getFilteredForumModel
 */
@Keep
@Serializable
data class FilterForumModel(
    val chatUid: String,
    val chatUserName: String,
    val chatUserEmail: String,
    val chatProfileUrl: String,
    val unreadMessageCount: Int,
)

/**
 * Get filtered forum model
 * Get filtered forum model to get chat user details
 * On the basis of uid it will return the user details which can be shown
 * to UI.
 * @param uid String uid of current user
 * @return FilterForumModel
 * @see FilterForumModel
 * @see ForumModel
 */
fun ForumModel.getFilteredForumModel(uid: String): FilterForumModel = if (uid == createdChatUid)
    FilterForumModel(
        chatUid = receiverChatUid,
        chatUserName = receiverChatUserName,
        chatUserEmail = receiverChatUserEmail,
        chatProfileUrl = receiverChatProfileUrl,
        unreadMessageCount = receiverUnreadMessageCount
    ) else
    FilterForumModel(
        chatUid = createdChatUid,
        chatUserName = createdChatUserName,
        chatUserEmail = createdChatUserEmail,
        chatProfileUrl = createdChatProfileUrl,
        unreadMessageCount = createdUnreadMessageCount
    )

/**
 * Message model
 * Base class to create new message in Forum
 * @property senderName String
 * @property senderUid String
 * @property receiverName String
 * @property receiverUid String
 * @property message String
 * @property path String
 * @property created Long
 * @constructor Create empty Message model
 * @see ForumRequest
 * @see ChatMessage
 */
@Keep
@Serializable
data class MessageModel(
    val senderName: String,
    val senderUid: String,
    val receiverName: String,
    val receiverUid: String,
    val message: String,
    val path: String = "",
    val created: Long = System.currentTimeMillis()
)


/**
 * Chat message
 * Use to create separate chat message for user and response
 * @constructor Create empty Chat message
 */
sealed class ChatMessage {
    abstract val senderName: String
    abstract val senderUid: String
    abstract val receiverName: String
    abstract val receiverUid: String
    abstract val message: String
    abstract val path: String
    abstract val created: Long

    /**
     * User chat message
     * Inherited from [ChatMessage]
     * @property senderName String
     * @property senderUid String
     * @property receiverName String
     * @property receiverUid String
     * @property message String
     * @property path String
     * @property created Long
     * @constructor Create empty User
     */
    data class User(
        override val senderName: String,
        override val senderUid: String,
        override val receiverName: String,
        override val receiverUid: String,
        override val message: String,
        override val path: String,
        override val created: Long
    ) : ChatMessage()

    /**
     * Response chat message
     * Inherited from [ChatMessage]
     * @property senderName String
     * @property senderUid String
     * @property receiverName String
     * @property receiverUid String
     * @property message String
     * @property path String
     * @property created Long
     * @constructor Create empty Response
     */
    data class Response(
        override val senderName: String,
        override val senderUid: String,
        override val receiverName: String,
        override val receiverUid: String,
        override val message: String,
        override val path: String,
        override val created: Long
    ) : ChatMessage()
}

/**
 * To chat message
 * Map [MessageModel] to [ChatMessage]
 * @param uid String UID of current user
 * @return List<ChatMessage>
 * @see ChatMessage
 * @see MessageModel
 */
fun List<MessageModel>.toChatMessage(
    uid: String
): List<ChatMessage> =
    this.map {
        when (uid) {
            it.senderUid -> ChatMessage.User(
                senderName = it.senderName,
                senderUid = it.senderUid,
                receiverName = it.receiverName,
                receiverUid = it.receiverUid,
                message = it.message,
                path = it.path,
                created = it.created
            )

            else -> ChatMessage.Response(
                senderName = it.senderName,
                senderUid = it.senderUid,
                receiverName = it.receiverName,
                receiverUid = it.receiverUid,
                message = it.message,
                path = it.path,
                created = it.created
            )
        }
    }

