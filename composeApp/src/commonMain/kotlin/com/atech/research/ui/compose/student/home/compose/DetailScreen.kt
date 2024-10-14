package com.atech.research.ui.compose.student.home.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AppRegistration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.atech.research.common.AsyncImage
import com.atech.research.common.BottomPadding
import com.atech.research.common.MainContainer
import com.atech.research.common.MarkdownViewer
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.getScreenHeight
import com.atech.research.utils.getScreenWidth
import com.atech.research.utils.removeExtraSpacesPreserveLineBreaks

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun DetailScreen(
    modifier: Modifier = Modifier,
    researchModel: ResearchModel?,
    onNavigationClick: (() -> Unit)? = null
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val navigationClick = if (researchModel != null) {
        onNavigationClick
    } else null
    MainContainer(
        modifier = modifier
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        enableTopBar = true,
        scrollBehavior = topAppBarScrollBehavior,
        onNavigationClick = navigationClick
    ) { contentPadding ->
        if (researchModel == null) {
            return@MainContainer
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(MaterialTheme.spacing.medium)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = researchModel.title.removeExtraSpacesPreserveLineBreaks(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
            )
            MarkdownViewer(
                markdown = researchModel.description.removeExtraSpacesPreserveLineBreaks(),
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
            )
            HorizontalDivider()
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Text(
                text = "Principal investigator: ${researchModel.author}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            ) {
                researchModel.tags.forEach {
                    FilterChip(selected = true,
                        onClick = {

                        },
                        label = { Text(text = it.name) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            TextButton(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Rounded.AppRegistration,
                    contentDescription = null,
                    modifier = Modifier.padding(end = MaterialTheme.spacing.small)
                )
                Text("Apply Now")
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            Surface(
                modifier = Modifier
                    .clickable {}
                    .fillMaxWidth()
                    .padding(bottom = MaterialTheme.spacing.medium)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        "To apply for this research opportunity, please contact the lead investigator for more details.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(
                                calculateImageSize(
                                    getScreenWidth().value,
                                    getScreenHeight().value
                                )
                            ),
                            url = researchModel.authorPhoto,
                            isLoadCircular = true,
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                        ) {
                            Text(text = "Lead Investigator: ${researchModel.author}")
                            Text(text = "Email: ${researchModel.authorEmail}")
                        }
                    }

                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = "Chat",
                            modifier = Modifier.padding(end = MaterialTheme.spacing.small)
                        )
                        Text("View Profile")
                    }

                }
            }
            BottomPadding()
            BottomPadding()
        }
    }
}

private fun calculateImageSize(screenWidth: Float, screenHeight: Float): Dp {
    val smallerDimension = minOf(screenWidth, screenHeight)
    return when {
        smallerDimension < 600f -> 80.dp  // For smaller screens
        smallerDimension < 900f -> 100.dp // For medium screens
        else -> 120.dp                    // For larger screens
    }
}