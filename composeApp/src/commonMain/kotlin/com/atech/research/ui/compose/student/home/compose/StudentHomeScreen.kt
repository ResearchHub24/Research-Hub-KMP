package com.atech.research.ui.compose.student.home.compose

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldScope
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.atech.research.common.MainContainer
import com.atech.research.common.ProgressBar
import com.atech.research.common.ResearchItem
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.ui.compose.profile.compose.ProfileScreen
import com.atech.research.ui.compose.student.home.StudentHomeEvents
import com.atech.research.ui.compose.student.home.StudentHomeViewModel
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.BackHandler
import com.atech.research.utils.DataState
import com.atech.research.utils.DeviceType
import com.atech.research.utils.getDisplayType
import com.atech.research.utils.koinViewModel
import org.jetbrains.compose.resources.stringResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.resume

private enum class ListScreenType {
    RESUME, LIST
}

@Composable
fun isBiggerDisplay(): Boolean = getDisplayType() != DeviceType.MOBILE

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun StudentHomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavController,
    canShowAppBar: (Boolean) -> Unit
) {
    val viewModel = koinViewModel<StudentHomeViewModel>()
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val allResearch by viewModel.allResearch
    val currentResearch by viewModel.selectedResearch


    var listScreenType by rememberSaveable { mutableStateOf(ListScreenType.LIST) }


    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    LaunchedEffect(navigator.currentDestination?.pane) {
        canShowAppBar(navigator.currentDestination?.pane == ThreePaneScaffoldRole.Secondary)
    }
    val detailPain: @Composable ThreePaneScaffoldScope.() -> Unit =
        if (listScreenType != ListScreenType.RESUME) {
            {
                AnimatedPane {
                    DetailScreen(researchModel = currentResearch, onNavigationClick = {
                        viewModel.onEvent(StudentHomeEvents.OnResearchClick(null))
                        navigator.navigateBack()
                    }, onApplyClick = {
                        listScreenType = ListScreenType.RESUME
                    })
                }
            }
        } else if (listScreenType == ListScreenType.RESUME && !isBiggerDisplay()) {
            {
                profileSection(
                    navHostController, {
                        listScreenType = ListScreenType.LIST
//                   navigator.navigateTo(pane = ListDetailPaneScaffoldRole.List, content = Unit)
                    },
                    questionList = currentResearch?.questions ?: emptyList()
                )
            }
        } else {
            {}
        }

    ListDetailPaneScaffold(modifier = modifier,
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            if (listScreenType == ListScreenType.LIST) {
                AnimatedPane {
                    ListScreen(items = allResearch, onItemClicked = {
                        viewModel.onEvent(StudentHomeEvents.OnResearchClick(it))
                        listScreenType = ListScreenType.LIST
                        navigator.navigateTo(pane = ListDetailPaneScaffoldRole.Detail, content = it)
                    })
                }
            }
            if (listScreenType == ListScreenType.RESUME && isBiggerDisplay()) {
                profileSection(
                    navHostController, {
                        listScreenType = ListScreenType.LIST
                    },
                    questionList = currentResearch?.questions ?: return@ListDetailPaneScaffold
                )
                return@ListDetailPaneScaffold
            }
        },
        detailPane = detailPain,
        extraPane = {
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
private fun ThreePaneScaffoldScope.profileSection(
    navHostController: NavController,
    onNavigateBack: () -> Unit,
    questionList: List<String> = emptyList(),
) {
    AnimatedPane {
        ProfileScreen(
            title = stringResource(Res.string.resume),
            navHostController = navHostController,
            isTeacher = false,
            fromDetailScreen = true,
            questionList = questionList,
            onNavigateBack = {
                onNavigateBack()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreen(
    modifier: Modifier = Modifier,
    items: DataState<List<ResearchModel>> = DataState.Loading,
    onItemClicked: (ResearchModel) -> Unit = {}
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    MainContainer(
        title = "Home",
        scrollBehavior = scrollBehavior,
        enableTopBar = true,
        customTopBar = {
            Row(
                modifier = Modifier.padding(
                    horizontal = if (!expanded) MaterialTheme.spacing.medium else MaterialTheme.spacing.default
                ).animateContentSize()
                    .background(TopAppBarDefaults.topAppBarColors().containerColor)
            ) {
                SearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    inputField = {
                        SearchBarDefaults.InputField(
                            onSearch = { expanded = false },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            placeholder = { Text("Search") },
                            leadingIcon = {
                                IconButton(onClick = {
                                    expanded = !expanded
                                }) {
                                    Icon(
                                        if (expanded) Icons.AutoMirrored.Default.ArrowBack else Icons.Default.Search,
                                        contentDescription = null
                                    )
                                }
                            },
                            trailingIcon = {
                                Icon(
                                    if (expanded) Icons.Default.FilterAlt else Icons.Default.AccountCircle,
                                    contentDescription = null
                                )
                            },
                            query = "",
                            onQueryChange = { },
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        repeat(4) { idx ->
                            val resultText = "Suggestion $idx"
                            ListItem(
                                headlineContent = { Text(resultText) },
                                supportingContent = { Text("Additional info") },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Star, contentDescription = null
                                    )
                                },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                modifier = Modifier.clickable {
//                                textFieldState.setTextAndPlaceCursorAtEnd(resultText)
                                    expanded = false
                                }.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier,
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.padding(
            ).nestedScroll(scrollBehavior.nestedScrollConnection), contentPadding = contentPadding
        ) {
            if (items is DataState.Loading) {
                item(key = "progress") {
                    ProgressBar(contentPadding)
                }
                return@LazyColumn
            }
            if (items is DataState.Error) {
                item(key = "error") {
                    Text(items.exception.message ?: "Something went wrong")
                }
                return@LazyColumn
            }
            item(key = "welcome") {
                WelcomeSection()
            }
            if (items is DataState.Success && items.data.isEmpty()) {
                item(key = "empty") {
                    Text("No research found")
                }
                return@LazyColumn
            }
            if (items is DataState.Success) {
                items(items = items.data) { research ->
                    ResearchItem(model = research, onClick = {
                        onItemClicked(research)
                    })
                }
            }
        }
    }
}

@Composable
private fun WelcomeSection() {
    Column(
        modifier = Modifier.padding(
            MaterialTheme.spacing.large, vertical = MaterialTheme.spacing.medium
        )
    ) {
        Text(
            "Welcome to Research Hub!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight(700)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            "Connect, Collaborate, and grow in academic research.",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight(400)
        )
    }
}