package com.atech.research.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Celebration
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.convertToDateFormat

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ResearchItem(
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