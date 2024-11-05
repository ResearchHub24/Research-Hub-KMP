package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Serializable
data class ForumRequest(
    val forumModel: ForumModel,
    val message: MessageModel?
)

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

    val path: String,
    val created: Long = System.currentTimeMillis()
)

@Keep
@Serializable
data class FilterForumModel(
    val chatUid: String,
    val chatUserName: String,
    val chatUserEmail: String,
    val chatProfileUrl: String,
    val unreadMessageCount: Int,
)


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


@Keep
@Serializable
data class MessageModel(
    val senderName: String,
    val senderUid: String,
    val receiverName: String,
    val receiverUid: String,
    val message: String,
    val path: String,
    val created: Long = System.currentTimeMillis()
)


sealed class ChatMessage {
    abstract val senderName: String
    abstract val senderUid: String
    abstract val receiverName: String
    abstract val receiverUid: String
    abstract val message: String
    abstract val path: String
    abstract val created: Long

    data class User(
        override val senderName: String,
        override val senderUid: String,
        override val receiverName: String,
        override val receiverUid: String,
        override val message: String,
        override val path: String,
        override val created: Long
    ) : ChatMessage()

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

