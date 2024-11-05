package com.atech.research.ui.compose.forum.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.atech.research.common.AsyncImage
import com.atech.research.common.MainContainer
import com.atech.research.core.ktor.model.ForumModel
import com.atech.research.core.ktor.model.getFilteredForumModel
import com.atech.research.ui.compose.main.IsUserTeacher
import com.atech.research.utils.BackHandler
import com.atech.research.utils.convertToDateFormat


@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(
    modifier: Modifier = Modifier,
    isAdmin: Boolean = IsUserTeacher(),
    canShowAppBar: (Boolean) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val scrollState = TopAppBarDefaults.pinnedScrollBehavior()
    LaunchedEffect(navigator.currentDestination?.pane) {
        canShowAppBar(navigator.currentDestination?.pane == ThreePaneScaffoldRole.Secondary)
    }
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    MainContainer(
        modifier = modifier.fillMaxSize()
            .nestedScroll(scrollState.nestedScrollConnection),
        enableTopBar = true,
        scrollBehavior = scrollState,
        title = "Forum",
        onNavigationClick = if (navigator.canNavigateBack()) {
            {
                navigator.navigateBack()
            }
        } else null
    ) { paddingValues ->
        ListDetailPaneScaffold(modifier = modifier
            .padding(paddingValues),
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                ForumScreenComposable(
                    isAdmin = isAdmin,
                    onChatClick = {}
                )
            },
            detailPane = {}
        )
    }
}


@Composable
private fun ForumScreenComposable(
    modifier: Modifier = Modifier,
    isAdmin: Boolean,
    chats: List<ForumModel> = emptyList(),
    onChatClick: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues ->
        if (chats.isEmpty()) {
            EmptyForumView(
                modifier = Modifier.padding(paddingValues),
                isAdmin = isAdmin
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(chats) { chat ->
                    AllForumItem(
                        chat = chat,
                        onClick = { onChatClick(chat.createdChatUid) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AllForumItem(
    chat: ForumModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    uid: String = "",
) {
    val model = chat.getFilteredForumModel(uid)
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    url = model.chatProfileUrl,
                    modifier = Modifier.fillMaxSize(),
                ) /*?: Text(
                    text = chat.name.first().toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )*/
            }

            // Chat Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chat.receiverChatUserName,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = chat.created.convertToDateFormat(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = model.chatUserEmail,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    if (model.unreadMessageCount > 0) {
                        Box(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(24.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = model.unreadMessageCount.toString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyForumView(
    modifier: Modifier = Modifier,
    isAdmin: Boolean
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Rounded.Forum,
            contentDescription = null,
            modifier = Modifier
                .size(72.dp)
                .padding(bottom = 16.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "No messages yet",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = if (isAdmin) "Select student to initiate a conversation." else "Awaiting a faculty member to start the conversation.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
