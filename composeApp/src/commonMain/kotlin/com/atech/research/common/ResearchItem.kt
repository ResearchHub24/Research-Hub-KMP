package com.atech.research.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PublicOff
import androidx.compose.material.icons.rounded.PublishedWithChanges
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.ui.theme.SelectedChipContainColor
import com.atech.research.ui.theme.SelectedChipContainerColor
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.convertToDateFormat
import com.atech.research.utils.markdownToPlainText
import org.jetbrains.compose.resources.stringResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.selected

@Composable
fun ResearchItem(
    modifier: Modifier = Modifier,
    model: ResearchModel,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        /*border = BorderStroke(.5.dp, MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))*/
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = model.title,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.extraSmall))
                        Text(
                            text = model.description.markdownToPlainText(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 5,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (isSelected) {
                        FilterChip(
                            label = { Text(stringResource(Res.string.selected)) },
                            onClick = { },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Celebration,
                                    contentDescription = null
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedLabelColor = SelectedChipContainColor,
                                selectedLeadingIconColor = SelectedChipContainColor,
                                selectedContainerColor = SelectedChipContainerColor
                            ),
                            selected = true
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = null,
                        modifier = Modifier.size(MaterialTheme.typography.bodyMedium.fontSize.value.dp)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                    Text(
                        text = model.author,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PublishedWithChanges,
                            contentDescription = null,
                            modifier = Modifier.size(MaterialTheme.typography.bodyLarge.fontSize.value.dp)
                        )
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.small))
                        Text(
                            text = model.created.convertToDateFormat(),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PublicOff,
                            contentDescription = null,
                            modifier = Modifier.size(
                                MaterialTheme.typography.bodyLarge.fontSize.value.dp
                            )
                        )
                        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))
                        Text(
                            text = model.deadline?.convertToDateFormat() ?: "No Deadline",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResearchTeacherItem(
    modifier: Modifier = Modifier,
    model: ResearchModel,
    isDeleteButtonVisible: Boolean = false,
    onClick: () -> Unit = {},
    onDeleted: () -> Unit = {},
    onViApplication: () -> Unit = {},
    onNotify: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium)
            ) {
                Text(
                    model.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
                Text(
                    model.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                ) {
                    model.tags.forEach {
                        FilterChip(selected = true,
                            onClick = {
                                onClick()
                            },
                            label = { Text(text = it.name) }
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
                Text(
                    text = if (model.deadline != null) "Deadline: ${model.deadline.convertToDateFormat()}"
                    else "Posted on: ${model.created.convertToDateFormat()}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    TextButton(
                        onClick = { onNotify() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Celebration,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
                        Text(text = "Notify")
                    }
                    TextButton(
                        onClick = { onViApplication() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.PendingActions,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
                        Text(text = "Applications")
                    }
                    if (isDeleteButtonVisible) {
                        TextButton(
                            onClick = { onDeleted() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.padding(MaterialTheme.spacing.extraSmall))
                            Text(text = "Delete")
                        }
                    }
                }
            }
        }
    }
}