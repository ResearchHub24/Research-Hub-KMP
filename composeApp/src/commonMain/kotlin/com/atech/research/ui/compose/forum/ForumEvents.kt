package com.atech.research.ui.compose.forum

import com.atech.research.core.ktor.model.ForumModel

sealed interface ForumEvents {
    data object LoadChat : ForumEvents
    data class OnChatClick(val forumModel: ForumModel) : ForumEvents
    data class OnMessageSend(val forumModel: ForumModel, val message: String) : ForumEvents
}