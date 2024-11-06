package com.atech.research.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.ui.compose.profile.compose.TopLayout
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.DataState
import org.jetbrains.compose.resources.stringResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.education
import researchhub.composeapp.generated.resources.link
import researchhub.composeapp.generated.resources.personal_details
import researchhub.composeapp.generated.resources.skill


/**
 * View profile
 * View profile of the user
 * @param modifier Modifier
 * @param user DataState<UserModel>
 * @param paddingValues PaddingValues?
 * @param enableTopBar Boolean
 * @param onNavigationClick () -> Unit
 * @see DataState
 * @see UserModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ViewProfile(
    modifier: Modifier = Modifier,
    user: DataState<UserModel>,
    paddingValues: PaddingValues?= null,
    enableTopBar: Boolean = true,
    onNavigationClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    MainContainer(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        scrollBehavior = scrollBehavior,
        enableTopBar = enableTopBar,
        onNavigationClick = onNavigationClick
    ) { paddingValue ->
        val innerPadding = paddingValues ?: paddingValue
        if (user is DataState.Loading) {
            ProgressBar(paddingValues = innerPadding)
            return@MainContainer
        }
        if (user is DataState.Error) {
//        Todo: Show error
            return@MainContainer
        }

        if (user is DataState.Success) {
            val extractedUser = user.data
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(MaterialTheme.spacing.medium)
            ) {
                CardSection(
                    title = stringResource(Res.string.personal_details),
                ) {
                    TopLayout(
                        name = extractedUser.displayName ?: "",
                        email = extractedUser.email ?: "",
                        profileImage = extractedUser.photoUrl ?: ""
                    )
                }
                if (extractedUser.educationDetails?.isNotEmpty() == true) {
                    Spacer(Modifier.height(MaterialTheme.spacing.medium))
                    CardSection(
                        title = stringResource(Res.string.education, ""),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            extractedUser.educationDetails.forEach { educationDetails ->
                                EducationDetailsItems(
                                    title = "${educationDetails.degree}\n${educationDetails.startYear} - ${educationDetails.endYear ?: "Present"} ${educationDetails.grade?.let { "( $it )" } ?: ""}",
                                    des = educationDetails.university,
                                    canShowButtons = false
                                )
                            }
                        }
                    }
                }
                if (extractedUser.skillList?.isNotEmpty() == true) {
                    Spacer(Modifier.height(MaterialTheme.spacing.medium))
                    CardSection(
                        title = stringResource(Res.string.skill),
                    ) {
                        extractedUser.skillList.forEach {
                            EducationDetailsItems(
                                title = it,
                                des = "",
                                canShowButtons = false
                            )
                        }
                    }
                }
                if (extractedUser.links?.isNotEmpty() == true) {
                    Spacer(Modifier.height(MaterialTheme.spacing.medium))
                    CardSection(
                        title = stringResource(Res.string.link),
                    ) {
                        extractedUser.links.forEach { linkModel ->
                            EducationDetailsItems(
                                title = linkModel.link,
                                des = linkModel.description,
                                isLink = true,
                                canShowButtons = false
                            )
                        }
                    }
                }
            }
        }
    }
}
