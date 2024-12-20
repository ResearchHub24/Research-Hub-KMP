package com.atech.research.ui.compose.student.application.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.atech.research.common.MainContainer
import com.atech.research.common.ProgressBar
import com.atech.research.common.ResearchApplicationItem
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.ui.compose.profile.compose.QuestionItem
import com.atech.research.ui.compose.student.application.ApplicationEvents
import com.atech.research.ui.compose.student.application.ApplicationViewModel
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.BackHandler
import com.atech.research.utils.DataState
import com.atech.research.utils.koinViewModel
import com.atech.research.utils.removeExtraSpacesPreserveLineBreaks

/**
 * Application Screen
 *
 * @param modifier Modifier
 * @param canShowAppBar (Boolean) -> Unit
 * @see ApplicationViewModel
 * @see UserModel
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ApplicationScreen(
    modifier: Modifier = Modifier,
    canShowAppBar: (Boolean) -> Unit
) {
    val scrollState = TopAppBarDefaults.pinnedScrollBehavior()
    val navigator = rememberListDetailPaneScaffoldNavigator<UserModel>()
    val viewModel: ApplicationViewModel = koinViewModel()
    val allApplications by viewModel.allApplications
    val selectedItem by viewModel.selectedApplication
    val pullToRefreshState = rememberPullToRefreshState()

    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    LaunchedEffect(navigator.currentDestination?.pane) {
        canShowAppBar(navigator.currentDestination?.pane == ThreePaneScaffoldRole.Secondary)
    }
    MainContainer(
        modifier = modifier.fillMaxSize()
            .nestedScroll(scrollState.nestedScrollConnection),
        state = pullToRefreshState,
        isRefreshing = allApplications is DataState.Loading,
        onRefresh = {
            viewModel.onEvent(ApplicationEvents.LoadApplication)
        },
        enableTopBar = true,
        scrollBehavior = scrollState,
        title = "Applications",
        onNavigationClick = if (navigator.canNavigateBack()) {
            {
                navigator.navigateBack()
            }
        } else null
    ) { paddingValues ->
        if (allApplications is DataState.Loading) {
            ProgressBar(
                paddingValues
            )
            return@MainContainer
        }
        ListDetailPaneScaffold(
            modifier = Modifier.fillMaxSize()
                .padding(MaterialTheme.spacing.medium)
                .padding(paddingValues),
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                val applications = allApplications as DataState.Success
                AnimatedPane {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {
                        items(applications.data) {
                            ResearchApplicationItem(
                                model = it
                            ) {
                                viewModel.onEvent(ApplicationEvents.ApplicationSelected(it))
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                            }
                        }
                    }
                }
            },
            detailPane = {
                if (selectedItem == null) {
                    return@ListDetailPaneScaffold
                }
                AnimatedPane {
                    LazyColumn {
                        item(key = "title") {
                            Text(
                                text = selectedItem!!.researchTitle.removeExtraSpacesPreserveLineBreaks(),
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
                            )
                        }
                        item(key = "Question") {
                            Text(
                                text = "Answers",
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
                            )
                        }
                        items(selectedItem!!.answers) {
                            QuestionItem(modifier = modifier.padding(bottom = MaterialTheme.spacing.medium),
                                question = it.question,
                                answer = it.answer,
                                readOnly = true,
                                onValueChange = {},
                                onClearClick = {})
                        }
                    }
                }
            }
        )
    }
}