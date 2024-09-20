package com.atech.research.ui.compose.teacher.home.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.atech.research.common.MainContainer
import com.atech.research.common.ProgressBar
import com.atech.research.common.ResearchItem
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.ui.compose.teacher.home.HomeScreenViewModel
import com.atech.research.utils.BackHandler
import com.atech.research.utils.DataState
import com.atech.research.utils.isAndroid
import com.atech.research.utils.koinViewModel


@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<HomeScreenViewModel>()
    val state = viewModel.researchModel.value
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
    MainContainer(modifier = modifier, title = title, enableTopBar = true, floatingActionButton = {
        AnimatedVisibility(
            !isAndroid() || navigator.currentDestination?.pane == ThreePaneScaffoldRole.Secondary,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            ExtendedFloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = null)
                Text(text = "Compose")
            }
        }
    }, onNavigationClick = if (navigator.canNavigateBack()) {
        { navigator.navigateBack() }
    } else null) { paddingValue ->
        if (state is DataState.Loading) {
            ProgressBar(
                paddingValues = paddingValue,
            )
            return@MainContainer
        }
        if (state is DataState.Error) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Error: ${state.exception.message}")
            }
            return@MainContainer
        }
        val researchList = (state as DataState.Success).data

        ListDetailPaneScaffold(modifier = Modifier.padding(paddingValue),
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(researchList) { research ->
                            ResearchItem(model = research, onClick = {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail, content = research
                                )
                            })
                        }
                    }
                }
            },
            detailPane = {
                val content = navigator.currentDestination?.content?.toString() ?: "No content"
                AnimatedPane {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text("Detail: $content")
                    }
                }
            })
    }
}