package com.atech.research.ui.compose.student.faculties.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.atech.research.common.MainContainer
import com.atech.research.common.ProgressBar
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.ui.compose.student.faculties.FacultiesEvent
import com.atech.research.ui.compose.student.faculties.FacultiesViewModel
import com.atech.research.common.ViewProfile
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.BackHandler
import com.atech.research.utils.DataState
import com.atech.research.utils.koinViewModel
import org.jetbrains.compose.resources.stringResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.faculties

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun FacultiesScreen(
    modifier: Modifier = Modifier,
    canShowAppBar: (Boolean) -> Unit
) {
    val scrollState = TopAppBarDefaults.pinnedScrollBehavior()
    val navigator = rememberListDetailPaneScaffoldNavigator<UserModel>()
    val viewModel = koinViewModel<FacultiesViewModel>()
    val allFaculties by viewModel.allFaculties
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    LaunchedEffect(navigator.currentDestination?.pane) {
        canShowAppBar(navigator.currentDestination?.pane == ThreePaneScaffoldRole.Secondary)
    }
    MainContainer(
        modifier = modifier.fillMaxSize()
            .nestedScroll(scrollState.nestedScrollConnection),
        enableTopBar = true,
        scrollBehavior = scrollState,
        title = stringResource(Res.string.faculties),
        onNavigationClick = if (navigator.canNavigateBack()) {
            {
                navigator.navigateBack()
            }
        } else null
    ) { paddingValues ->
        if (allFaculties is DataState.Loading) {
            ProgressBar(
                paddingValues
            )
            return@MainContainer
        }
        ListDetailPaneScaffold(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    val data = (allFaculties as? DataState.Success)?.data ?: emptyList()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(MaterialTheme.spacing.medium),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {
                        items(data) { faculty ->
                            FacultyItem(
                                userModel = faculty,
                                onClick = {
                                    viewModel.onEvent(
                                        FacultiesEvent.FacultySelected(faculty)
                                    )
                                    navigator.navigateTo(
                                        ListDetailPaneScaffoldRole.Detail
                                    )
                                }
                            )
                        }
                    }
                }
            },
            detailPane = {
                AnimatedPane {
                    val selectedUser = viewModel.selectedUser.value
                    if (selectedUser != null) {
                        ViewProfile(
                            enableTopBar = false,
                            paddingValues = PaddingValues(0.dp),
                            user = DataState.Success(selectedUser),
                            onNavigationClick = {
                                viewModel.onEvent(FacultiesEvent.FacultySelected(null))
                                navigator.navigateBack()
                            }
                        )
                    }
                }
            }
        )
    }
}