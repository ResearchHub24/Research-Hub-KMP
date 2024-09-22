package com.atech.research.ui.compose.teacher.home.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.atech.research.LocalDataStore
import com.atech.research.common.BottomPadding
import com.atech.research.common.EmptyWelcomeScreen
import com.atech.research.common.MainContainer
import com.atech.research.common.MarkdownViewer
import com.atech.research.common.ProgressBar
import com.atech.research.common.ResearchItem
import com.atech.research.common.bottomPaddingLazy
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.ui.compose.teacher.home.HomeScreenEvents
import com.atech.research.ui.compose.teacher.home.HomeScreenViewModel
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.BackHandler
import com.atech.research.utils.DataState
import com.atech.research.utils.Prefs
import com.atech.research.utils.koinViewModel

enum class TerritoryScreen {
    ViewMarkdown, EditTag
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val uid = LocalDataStore.current.getString(Prefs.USER_ID.name)
    val viewModel = koinViewModel<HomeScreenViewModel>()
    LaunchedEffect(uid) {
        viewModel.onEvent(HomeScreenEvents.SetUserId(uid))
    }
    val allResearch by viewModel.allResearch
    val currentResearch by viewModel.currentResearchModel

    val allTags by viewModel.allTags
    var territoryScreen by rememberSaveable { mutableStateOf(TerritoryScreen.ViewMarkdown) }
    val navigator = rememberListDetailPaneScaffoldNavigator<ResearchModel>()
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    val title: String = when (navigator.currentDestination?.pane) {
        ThreePaneScaffoldRole.Secondary -> "Posted Research"
        ThreePaneScaffoldRole.Primary -> "Detail"
        ThreePaneScaffoldRole.Tertiary -> ""
        null -> {
            ""
        }
    }
    MainContainer(modifier = modifier,
        title = title,
        enableTopBar = true,
        onNavigationClick = if (navigator.canNavigateBack()) {
            {
                navigator.navigateBack()
            }
        } else null) { paddingValue ->
        if (allResearch is DataState.Loading) {
            ProgressBar(
                paddingValues = paddingValue,
            )
            return@MainContainer
        }
        if (allResearch is DataState.Error) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Error: ${(allResearch as DataState.Error).exception.message}")
            }
            return@MainContainer
        }
        val researchList = (allResearch as DataState.Success).data
        val state: LazyListState = rememberLazyListState()
        ListDetailPaneScaffold(modifier = Modifier.padding(paddingValue),
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    Scaffold(floatingActionButton = {
                        ExtendedFloatingActionButton(onClick = {}) {
                            Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
                            Text(text = "Compose")
                        }
                    }) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(), state = state
                        ) {
                            items(researchList) { research ->
                                ResearchItem(model = research, onClick = {
                                    viewModel.onEvent(HomeScreenEvents.SetResearch(research))
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail, content = research
                                    )
                                })
                            }
                            bottomPaddingLazy("pad1")
                            bottomPaddingLazy("pad2")
                        }
                    }
                }
            },
            detailPane = {

                AnimatedPane {
                    if (currentResearch == null) {
                        EmptyWelcomeScreen()
                        return@AnimatedPane
                    }
                    EditScreen(
                        model = currentResearch!!,
                        isSaveButtonVisible = navigator.currentDestination?.pane == ListDetailPaneScaffoldRole.Detail,
                        onTitleChange = {
                            viewModel.onEvent(
                                HomeScreenEvents.OnEdit(
                                    currentResearch!!.copy(
                                        title = it
                                    )
                                )
                            )
                        },
                        onDescriptionChange = {
                            viewModel.onEvent(
                                HomeScreenEvents.OnEdit(
                                    currentResearch!!.copy(description = it)
                                )
                            )
                        },
                        onQuestionChange = {
                            viewModel.onEvent(
                                HomeScreenEvents.OnEdit(
                                    currentResearch!!.copy(questions = it)
                                )
                            )
                        },
                        onSaveClick = {
                            viewModel.onEvent(HomeScreenEvents.SaveChanges {
                                while (navigator.canNavigateBack()) {
                                    navigator.navigateBack()
                                }
                            })
                        },
                        onViewMarkdownClick = {
                            territoryScreen = TerritoryScreen.ViewMarkdown
                            navigator.navigateTo(
                                pane = ThreePaneScaffoldRole.Tertiary, content = currentResearch
                            )
                        },
                        onAddTagClick = {
                            territoryScreen = TerritoryScreen.EditTag
                            navigator.navigateTo(
                                pane = ThreePaneScaffoldRole.Tertiary, content = currentResearch
                            )
                        }
                    )
                }
            },
            extraPane = {
                var hasError by rememberSaveable { mutableStateOf<String?>(null) }
                AnimatedPane {
                    AnimatedVisibility(territoryScreen == TerritoryScreen.EditTag) {
                        if (allTags is DataState.Error) {
                            Text("Error: ${(allTags as DataState.Error).exception.message}")
                            return@AnimatedVisibility
                        }
                        if (allTags is DataState.Success) {
                            val tags = (allTags as DataState.Success).data
                            TagScreen(
                                list = tags,
                                selectedList = currentResearch?.tags ?: emptyList(),
                                onSelectOrRemoveTag = {
                                    viewModel.onEvent(
                                        HomeScreenEvents.OnEdit(
                                            currentResearch!!.copy(tags = it)
                                        )
                                    )
                                },
                                onAddTag = { model ->
                                    viewModel.onEvent(
                                        HomeScreenEvents.AddTag(model, onDone = { exception ->
                                            exception?.message.also { hasError = it }
                                                ?: run { hasError = null }
                                        })
                                    )
                                },
                                error = hasError ?: ""
                            )
                        }
                    }
//                    ViewMarkdownScreen
                    AnimatedVisibility(territoryScreen == TerritoryScreen.ViewMarkdown) {
                        val markdown = currentResearch?.description ?: "No Description Available"
                        Column(
                            modifier = Modifier.fillMaxSize().padding(MaterialTheme.spacing.medium)
                                .verticalScroll(rememberScrollState())
                        ) {
                            MarkdownViewer(
                                markdown = markdown
                            )
                            BottomPadding()
                        }
                    }
                }
            })

    }
}