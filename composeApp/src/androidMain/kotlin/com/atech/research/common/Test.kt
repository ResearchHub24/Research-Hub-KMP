package com.atech.research.common

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.atech.research.core.ktor.model.ForumModel
import com.atech.research.ui.compose.forum.compose.ForumScreen
import com.atech.research.ui.theme.ResearchHubTheme


@Preview()
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun TestPreview() {
    ResearchHubTheme {
        val sampleChats = listOf(
            ForumModel(
                createdChatUid = "uid_001",
                createdChatUserName = "Alice",
                createdChatUserEmail = "alice@example.com",
                createdChatProfileUrl = "https://example.com/profiles/alice.jpg",
                createdUnreadMessageCount = 2,

                receiverChatUid = "uid_002",
                receiverChatUserName = "Bob",
                receiverChatUserEmail = "bob@example.com",
                receiverChatProfileUrl = "https://example.com/profiles/bob.jpg",
                receiverUnreadMessageCount = 1,

                path = "/forums/001"
            ),
            ForumModel(
                createdChatUid = "uid_003",
                createdChatUserName = "Charlie",
                createdChatUserEmail = "charlie@example.com",
                createdChatProfileUrl = "https://example.com/profiles/charlie.jpg",
                createdUnreadMessageCount = 0,

                receiverChatUid = "uid_004",
                receiverChatUserName = "Daisy",
                receiverChatUserEmail = "daisy@example.com",
                receiverChatProfileUrl = "https://example.com/profiles/daisy.jpg",
                receiverUnreadMessageCount = 3,

                path = "/forums/002"
            ),
            ForumModel(
                createdChatUid = "uid_005",
                createdChatUserName = "Eve",
                createdChatUserEmail = "eve@example.com",
                createdChatProfileUrl = "https://example.com/profiles/eve.jpg",
                createdUnreadMessageCount = 5,

                receiverChatUid = "uid_006",
                receiverChatUserName = "Frank",
                receiverChatUserEmail = "frank@example.com",
                receiverChatProfileUrl = "https://example.com/profiles/frank.jpg",
                receiverUnreadMessageCount = 0,

                path = "/forums/003"
            )
        )
        ForumScreen(
            chats = sampleChats,
            onChatClick = {},
        )
    }
}
