package com.atech.research.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.atech.research.core.ktor.model.Action
import com.atech.research.core.ktor.model.ApplicationModel
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.convertToDateFormat


@Composable
fun ResearchApplicationItem(
    modifier: Modifier = Modifier,
    model: ApplicationModel,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        OutlinedCard(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = when (model.action) {
                    Action.SELECTED -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                    Action.REJECTED -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                    Action.PENDING -> MaterialTheme.colorScheme.surface
                }
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Left section: Title and Status
                Column(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
                ) {
                    Text(
                        text = model.researchTitle,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Application Status
                    Text(
                        text = when (model.action) {
                            Action.SELECTED -> "Selected"
                            Action.REJECTED -> "Not Selected"
                            Action.PENDING -> "Under Review"
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = when (model.action) {
                            Action.SELECTED -> MaterialTheme.colorScheme.primary
                            Action.REJECTED -> MaterialTheme.colorScheme.error
                            Action.PENDING -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }

                // Right section: Status Icon and Dates
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = MaterialTheme.spacing.medium)
                        .defaultMinSize(minWidth = 160.dp)
                ) {
                    // Dates
                    Text(
                        text = "Applied on ${model.created.convertToDateFormat()}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(end = MaterialTheme.spacing.small)
                    )

                    // Status Icon
                    when (model.action) {
                        Action.SELECTED -> Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(start = MaterialTheme.spacing.medium)
                                .size(24.dp)
                        )

                        Action.REJECTED -> Icon(
                            imageVector = Icons.Rounded.Cancel,
                            contentDescription = "Not Selected",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .padding(start = MaterialTheme.spacing.medium)
                                .size(24.dp)
                        )

                        Action.PENDING -> Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = "Under Review",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .padding(start = MaterialTheme.spacing.medium)
                                .size(24.dp)
                        )
                    }
                }
            }
        }
    }
}