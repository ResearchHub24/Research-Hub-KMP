package com.atech.research.ui.compose.profile.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.atech.research.common.AppAlertDialog
import com.atech.research.common.ApplyButton
import com.atech.research.common.AsyncImage
import com.atech.research.common.CardSection
import com.atech.research.common.DisplayCard
import com.atech.research.common.EducationDetailsItems
import com.atech.research.common.MainContainer
import com.atech.research.common.ProgressBar
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.ui.compose.profile.ProfileEvents
import com.atech.research.ui.compose.profile.ProfileViewModel
import com.atech.research.ui.navigation.ResearchHubNavigation
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.BackHandler
import com.atech.research.utils.DataLoader
import com.atech.research.utils.DataLoaderImpl
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.koinViewModel
import com.atech.research.utils.researchHubLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.stringResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.add
import researchhub.composeapp.generated.resources.apply
import researchhub.composeapp.generated.resources.complete_your_profile
import researchhub.composeapp.generated.resources.delete
import researchhub.composeapp.generated.resources.education
import researchhub.composeapp.generated.resources.education_delete_message
import researchhub.composeapp.generated.resources.link
import researchhub.composeapp.generated.resources.log_out
import researchhub.composeapp.generated.resources.personal_details
import researchhub.composeapp.generated.resources.profile
import researchhub.composeapp.generated.resources.skill

/**
 * Screen type
 * This is the screen type.
 * @constructor Create empty Screen type
 */
private enum class ScreenType {
    EDUCATION, LINK, SKILL, QUESTION
}

/**
 * Profile screen
 * This is the profile screen.
 * @param modifier Modifier
 * @param title String
 * @param isTeacher Boolean
 * @param navHostController NavController
 * @param fromDetailScreen Boolean
 * @param questionList List<String>
 * @param researchPath String
 * @param researchTitle String
 * @param onNavigateBack Function0<Unit>
 * @see ProfileViewModel
 * @see DataLoader
 * @see BackHandler
 * @see ProfileEvents
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.profile),
    isTeacher: Boolean = true,
    navHostController: NavController,
    fromDetailScreen: Boolean = false,
    questionList: List<String> = emptyList(),
    researchPath: String = "",
    researchTitle: String = "",
    onNavigateBack: (() -> Unit) = {}
) {
    val viewModel = koinViewModel<ProfileViewModel>()
    val userDataState by viewModel.user
    val appBarBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scrollState = rememberScrollState()
    val navigator = rememberListDetailPaneScaffoldNavigator<UserModel>()
    var isDeleteEducationDialogVisible by remember { mutableStateOf(false) }
    var screenType by remember { mutableStateOf(ScreenType.EDUCATION) }
    var deleteType by remember { mutableStateOf(ScreenType.EDUCATION) }
    val dataLoader: DataLoader by DataLoaderImpl()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    dataLoader.setTask { viewModel.onEvent(ProfileEvents.LoadData) }
    dataLoader.registerLifecycleOwner(lifecycleOwner)
    BackHandler(!navigator.canNavigateBack() && fromDetailScreen) {
        onNavigateBack()
    }
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    MainContainer(modifier = modifier.nestedScroll(appBarBehavior.nestedScrollConnection),
        title = title,
        scrollBehavior = appBarBehavior,
        enableTopBar = true,
        onNavigationClick = if (navigator.canNavigateBack() || fromDetailScreen) {
            {
                if (!navigator.canNavigateBack()) onNavigateBack()
                viewModel.onEvent(ProfileEvents.OnEditClick(null))
                viewModel.onEvent(ProfileEvents.OnLinkClick(null))
                navigator.navigateBack()
            }
        } else null) { paddingValue ->
        if (userDataState is DataState.Loading) {
            ProgressBar(paddingValue)
            return@MainContainer
        }
        if (userDataState is DataState.Success) {
            val user = (userDataState as DataState.Success).data
            ListDetailPaneScaffold(modifier = modifier.padding(paddingValue),
                directive = navigator.scaffoldDirective,
                value = navigator.scaffoldValue,
                listPane = {
//                    navigator.navigateTo(ThreePaneScaffoldRole.Primary)
                    AnimatedPane {
                        Column(
                            modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
                                .padding(MaterialTheme.spacing.medium)
                        ) {
                            AnimatedVisibility(isDeleteEducationDialogVisible) {
                                AppAlertDialog(icon = Icons.Outlined.Warning,
                                    dialogTitle = stringResource(
                                        if (deleteType == ScreenType.LINK) Res.string.link
                                        else Res.string.education, stringResource(Res.string.delete)
                                    ),
                                    dialogText = stringResource(Res.string.education_delete_message),
                                    onDismissRequest = {
                                        isDeleteEducationDialogVisible = false
                                    },
                                    onConfirmation = {
                                        if (deleteType == ScreenType.LINK) {
                                            viewModel.onEvent(
                                                ProfileEvents.OnDeleteLinkClick(linkList = user.links
                                                    ?: emptyList(),
                                                    yetToDelete = viewModel.currentLinkClick.value
                                                        ?: return@AppAlertDialog,
                                                    onComplete = {
//                                                        Todo: Handle Error
                                                        viewModel.onEvent(
                                                            ProfileEvents.OnLinkClick(
                                                                null
                                                            )
                                                        )
                                                        isDeleteEducationDialogVisible = false
                                                    })
                                            )
                                            return@AppAlertDialog
                                        }
                                        viewModel.onEvent(
                                            ProfileEvents.OnDeleteEducationClick(
                                                educationDetailsList = user.educationDetails
                                                    ?: emptyList(),
                                                yetToDelete = viewModel.currentEducationDetails.value
                                                    ?: return@AppAlertDialog,
                                                onComplete = {
//                                                Todo: Handle Error
                                                    viewModel.onEvent(ProfileEvents.OnEditClick(null))
                                                    isDeleteEducationDialogVisible = false
                                                })
                                        )
                                    })
                            }

                            CardSection(
                                title = stringResource(Res.string.personal_details),
                            ) {
                                TopLayout(
                                    name = user.displayName ?: "",
                                    email = user.email ?: "",
                                    profileImage = user.photoUrl ?: ""
                                )
                            }
                            Spacer(Modifier.height(MaterialTheme.spacing.medium))
                            CardSection(
                                title = stringResource(Res.string.education, ""),
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    user.educationDetails?.forEach { educationDetails ->
                                        EducationDetailsItems(title = "${educationDetails.degree}\n${educationDetails.startYear} - ${educationDetails.endYear ?: "Present"} ${educationDetails.grade?.let { "( $it )" } ?: ""}",
                                            des = educationDetails.university,
                                            onEditClick = {
//                                                Todo : Handle Error
                                                viewModel.onEvent(
                                                    ProfileEvents.OnEditClick(
                                                        educationDetails
                                                    )
                                                )
                                                navigator.navigateTo(
                                                    pane = ListDetailPaneScaffoldRole.Extra
                                                )
                                            },
                                            onDeleteClick = {
                                                deleteType = ScreenType.EDUCATION
                                                viewModel.onEvent(
                                                    ProfileEvents.OnEditClick(
                                                        educationDetails
                                                    )
                                                )
                                                isDeleteEducationDialogVisible = true
                                            })
                                    }
                                    Spacer(Modifier.height(MaterialTheme.spacing.medium))
                                    ApplyButton(text = stringResource(
                                        Res.string.add, stringResource(Res.string.education)
                                    ), action = {
                                        screenType = ScreenType.EDUCATION
                                        viewModel.onEvent(
                                            ProfileEvents.OnEditClick(
                                                com.atech.research.core.ktor.model.EducationDetails()
                                            )
                                        )
                                        navigator.navigateTo(
                                            pane = ListDetailPaneScaffoldRole.Extra
                                        )
                                    })
                                }
                            }
                            Spacer(Modifier.height(MaterialTheme.spacing.medium))
                            CardSection(
                                title = stringResource(Res.string.skill),
                            ) {
                                user.skillList?.forEach {
                                    EducationDetailsItems(
                                        title = it,
                                        des = "",
                                        canShowButtons = true,
                                        canShowEditButton = false
                                    )
                                }
                                Spacer(Modifier.height(MaterialTheme.spacing.medium))
                                ApplyButton(text = stringResource(
                                    Res.string.add, stringResource(Res.string.skill)
                                ), action = {
                                    screenType = ScreenType.SKILL
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Extra
                                    )
                                })
                            }

                            if (isTeacher) {
                                Spacer(Modifier.height(MaterialTheme.spacing.medium))
                                CardSection(
                                    title = stringResource(Res.string.link),
                                ) {
                                    user.links?.forEach { linkModel ->
                                        EducationDetailsItems(title = linkModel.link,
                                            des = linkModel.description,
                                            isLink = true,
                                            onEditClick = {
                                                screenType = ScreenType.LINK
                                                viewModel.onEvent(
                                                    ProfileEvents.OnLinkClick(
                                                        linkModel
                                                    )
                                                )
                                                navigator.navigateTo(
                                                    pane = ListDetailPaneScaffoldRole.Extra
                                                )
                                            },
                                            onDeleteClick = {
                                                deleteType = ScreenType.LINK
                                                viewModel.onEvent(
                                                    ProfileEvents.OnLinkClick(
                                                        linkModel
                                                    )
                                                )
                                                isDeleteEducationDialogVisible = true
                                            })
                                    }
                                    Spacer(Modifier.height(MaterialTheme.spacing.medium))
                                    ApplyButton(text = stringResource(
                                        Res.string.add, stringResource(Res.string.link),
                                    ), action = {
                                        viewModel.onEvent(
                                            ProfileEvents.OnLinkClick(
                                                null
                                            )
                                        )
                                        screenType = ScreenType.LINK
                                        navigator.navigateTo(
                                            pane = ListDetailPaneScaffoldRole.Extra
                                        )
                                    })
                                }
                            }

                            if (!fromDetailScreen) {
                                Spacer(Modifier.height(MaterialTheme.spacing.large))
                                ApplyButton(text = stringResource(Res.string.log_out), action = {
//                                Todo: Shakya - Implement Alert Dialog
                                    viewModel.onEvent(ProfileEvents.PerformSignOut {
                                        if (it != null) {
                                            researchHubLog(
                                                ResearchLogLevel.ERROR, "ProfileScreen $it"
                                            )
//                                        Todo: Handle Error
                                            return@PerformSignOut
                                        }
                                        runBlocking(Dispatchers.Main) {
                                            navHostController.navigate(
                                                ResearchHubNavigation.LogInScreen.route
                                            ) {
                                                popUpTo(ResearchHubNavigation.MainScreen.route) {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    })
                                })
                            }
                            if (fromDetailScreen) {
                                val hasError =
                                    user.educationDetails?.isNotEmpty() ?: false && user.skillList?.isNotEmpty() ?: false
                                Spacer(Modifier.height(MaterialTheme.spacing.large))
                                AnimatedVisibility(!hasError) {
                                    DisplayCard(
                                        modifier = Modifier.fillMaxSize()
                                            .padding(MaterialTheme.spacing.medium),
                                        border = BorderStroke(
                                            width = CardDefaults.outlinedCardBorder().width,
                                            color = MaterialTheme.colorScheme.error
                                        ),
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.ErrorOutline,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                            Text(
                                                text = stringResource(Res.string.complete_your_profile),
                                                modifier = Modifier.padding(MaterialTheme.spacing.medium),
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                                Spacer(Modifier.height(MaterialTheme.spacing.large))
                                ApplyButton(
                                    enable = hasError,
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalPadding = MaterialTheme.spacing.medium,
                                    text = stringResource(Res.string.apply),
                                    action = {
                                        screenType = ScreenType.QUESTION
                                        navigator.navigateTo(
                                            pane = ListDetailPaneScaffoldRole.Extra
                                        )
                                    }
                                )
                            }
                        }
                    }
                },
                detailPane = {},
                extraPane = {
                    AnimatedPane {
                        AnimatedVisibility(screenType == ScreenType.EDUCATION) {
                            val currentItem by viewModel.currentEducationDetails
                            if (currentItem == null) return@AnimatedVisibility
                            EducationDetails(state = currentItem!!,
                                onEvent = viewModel::onEvent,
                                onSaveClick = {
                                    viewModel.onEvent(
                                        ProfileEvents.OnSaveEducationClick(educationDetailsList = user.educationDetails
                                            ?: emptyList(), onComplete = {
                                            researchHubLog(
                                                ResearchLogLevel.DEBUG, "ProfileScreen $it"
                                            )
                                            navigator.navigateBack()
                                        })
                                    )
                                })
                        }

                        AnimatedVisibility(screenType == ScreenType.LINK) {
                            AddOrEditLink(state = viewModel.currentLinkClick.value, onLinkSave = {
                                viewModel.onEvent(
                                    ProfileEvents.OnAddLinkClick(linkList = user.links
                                        ?: emptyList(),
                                        link = it,
                                        onComplete = { error ->
                                            if (error != null) {
//                                                TODO: Handle Error
                                                researchHubLog(
                                                    ResearchLogLevel.DEBUG, "ProfileScreen $error"
                                                )
                                            }

                                            navigator.navigateBack()
                                        })
                                )
                            })
                        }

                        AnimatedVisibility(screenType == ScreenType.SKILL) {
                            viewModel.onEvent(ProfileEvents.LoadSkillList)
                            val skill by viewModel.skillList
                            if (skill is DataState.Loading) {
                                ProgressBar(paddingValue)
                                return@AnimatedVisibility
                            }
                            if (skill is DataState.Success) {
                                AddOrEditSkill(
                                    skillList = (skill as DataState.Success).data,
                                    selectedList = user.skillList ?: emptyList(),
                                    onDoneClick = {
                                        viewModel.onEvent(
                                            ProfileEvents.OnAddSkillClick(
                                                skillList = it,
                                                onComplete = { error ->
//                                                    TODO: Handle Error
                                                    if (error != null) {
                                                        researchHubLog(
                                                            ResearchLogLevel.DEBUG,
                                                            "ProfileScreen $error"
                                                        )
                                                    }
                                                    navigator.navigateBack()
                                                }
                                            )
                                        )
                                    }
                                )
                            }
                        }

                        AnimatedVisibility(screenType == ScreenType.QUESTION) {
                            QuestionScreen(
                                questionList = questionList,
                            ) { answerList ->
                                viewModel.onEvent(
                                    ProfileEvents.ApplyResearch(
                                        researchId = researchPath,
                                        answerModelList = answerList,
                                        researchTitle = researchTitle
                                    ) {
                                        if (it != null) {
                                            researchHubLog(
                                                ResearchLogLevel.ERROR, "ProfileScreen $it"
                                            )
                                        }
                                        navigator.navigateBack()
                                    }
                                )
                            }
                        }
                    }
                })
        }
    }
}

/**
 * Top layout
 * This is the top layout of [ProfileScreen].
 * @param modifier Modifier
 * @param name String
 * @param email String
 * @param profileImage String
 */
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