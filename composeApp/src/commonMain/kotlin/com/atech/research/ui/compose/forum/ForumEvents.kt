package com.atech.research.ui.compose.forum

sealed interface ForumEvents {
    data object LoadChat : ForumEvents
    data class OnChatClick(val path: String) : ForumEvents
}