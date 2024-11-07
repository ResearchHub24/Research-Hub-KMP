package com.atech.research.ui.compose.forum

import com.atech.research.core.ktor.model.ForumModel

/**
 * Forum events
 * This is the sealed interface that represents the events in the forum.
 */
sealed interface ForumEvents {
    /**
     * Load chat event
     * This event is called when the chat is loaded.
     */
    data object LoadChat : ForumEvents

    /**
     * On chat click event
     * This event is called when a chat is clicked.
     * @param forumModel The model of the chat
     */
    data class OnChatClick(val forumModel: ForumModel) : ForumEvents

    /**
     * On message send event
     * This event is called when a message is sent.
     * @param forumModel The model of the chat
     * @param message The message to be sent
     */
    data class OnMessageSend(val forumModel: ForumModel, val message: String) : ForumEvents
}