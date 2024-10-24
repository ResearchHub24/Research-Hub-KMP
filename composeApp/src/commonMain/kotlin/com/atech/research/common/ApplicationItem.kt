package com.atech.research.common

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
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
        val isSelected = model.selected
        OutlinedCard(
            modifier = modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected)
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                else MaterialTheme.colorScheme.surface
            ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Left section: Title
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
                }

                // Right section: Status and Dates
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = MaterialTheme.spacing.medium)
                        .defaultMinSize(minWidth = 160.dp)  // Slightly increased for new text
                ) {
                    // Dates Column
                    Text(
                        text = "Applied on ${model.created.convertToDateFormat()}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(end = MaterialTheme.spacing.small)
                    )

                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.primary,
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