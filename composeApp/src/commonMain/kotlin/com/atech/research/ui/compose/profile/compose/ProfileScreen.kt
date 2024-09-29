package com.atech.research.ui.compose.profile.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.atech.research.common.AsyncImage
import com.atech.research.common.CardSection
import com.atech.research.common.EducationDetailsItems
import com.atech.research.common.MainContainer
import com.atech.research.common.ProgressBar
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.ui.compose.profile.ProfileEvents
import com.atech.research.ui.compose.profile.ProfileViewModel
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.BackHandler
import com.atech.research.utils.DataState
import com.atech.research.utils.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier, forTeacher: Boolean = true
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val userDataState by viewModel.user
    val appBarBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberScrollState()
    val navigator = rememberListDetailPaneScaffoldNavigator<UserModel>()
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    MainContainer(modifier = modifier
        .nestedScroll(appBarBehavior.nestedScrollConnection),
        title = "Profile",
        scrollBehavior = appBarBehavior,
        enableTopBar = true,
        onNavigationClick = if (navigator.canNavigateBack()) {
            {
                viewModel.onEvent(ProfileEvents.OnEditClick(null))
                navigator.navigateBack()
            }
        } else null) { paddingValue ->
        if (userDataState is DataState.Loading) {
            ProgressBar(paddingValue)
            return@MainContainer
        }
        if (userDataState is DataState.Success) {
            val user = (userDataState as DataState.Success).data
            ListDetailPaneScaffold(
                modifier = modifier.padding(paddingValue),
                directive = navigator.scaffoldDirective,
                value = navigator.scaffoldValue,
                listPane = {
//                    navigator.navigateTo(ThreePaneScaffoldRole.Primary)
                    AnimatedPane {
                        Column(
                            modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
                                .padding(paddingValue).padding(MaterialTheme.spacing.medium)
                        ) {
                            CardSection(
                                title = "Personal Information",
                            ) {
                                TopLayout(
                                    name = user.displayName ?: "",
                                    email = user.email ?: "",
                                    profileImage = user.photoUrl ?: ""
                                )
                            }
                            Spacer(Modifier.height(MaterialTheme.spacing.medium))
                            CardSection(
                                title = "Education",
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    user.educationDetails?.forEach { educationDetails ->
                                        EducationDetailsItems(title = "${educationDetails.degree}\n${educationDetails.startYear} - ${educationDetails.endYear ?: "Present"} ${educationDetails.grade?.let { "( $it )" } ?: ""}",
                                            des = educationDetails.university,
                                            onEditClick = {
                                                viewModel.onEvent(
                                                    ProfileEvents.OnEditClick(
                                                        educationDetails
                                                    )
                                                )
                                                navigator.navigateTo(
                                                    pane = ListDetailPaneScaffoldRole.Extra
                                                )
                                            })
                                    }
                                }
                            }
                            Spacer(Modifier.height(MaterialTheme.spacing.medium))
                            CardSection(
                                title = "Links",
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth().height(500.dp)
                                        .background(MaterialTheme.colorScheme.surface)
                                ) {
                                    Text(
                                        text = "Link",
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                },
                detailPane = {

                },
                extraPane = {
                    AnimatedPane {
                        val currentItem by viewModel.currentEducationDetails
                        if (currentItem == null) return@AnimatedPane

                        EducationDetails(
                            state = currentItem!!,
                            onEvent = viewModel::onEvent
                        )
                    }
                }
            )
        }
    }
}


@Composable
fun TopLayout(
    modifier: Modifier = Modifier, name: String, email: String, profileImage: String
) {
    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight(.4f),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(200.dp),
            url = profileImage,
            isLoadCircular = true,
        )
        Spacer(Modifier.height(MaterialTheme.spacing.medium))
        Text(
            text = name, style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(MaterialTheme.spacing.medium))
        Text(
            text = email, style = MaterialTheme.typography.titleMedium
        )
    }
}