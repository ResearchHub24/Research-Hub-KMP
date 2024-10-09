package com.atech.research.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.atech.research.ui.theme.captionColor
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.LinkHelper
import org.koin.compose.koinInject

@Composable
fun EducationDetailsItems(
    modifier: Modifier = Modifier,
    title: String,
    des: String,
    isLink: Boolean = false,
    canShowButtons: Boolean = true,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val linkHelper = koinInject<LinkHelper>()
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier.weight(1f),
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        if (isLink) {
                            linkHelper.openLink(title)
                        }
                    },
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (isLink) 1 else Int.MAX_VALUE,
                overflow = if (isLink) TextOverflow.Ellipsis else TextOverflow.Clip
            )
            if (des.isNotEmpty()) {
                Spacer(modifier = Modifier.size(MaterialTheme.spacing.small))
                Text(
                    text = des,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.captionColor,
                    maxLines = if (isLink) 3 else Int.MAX_VALUE,
                    overflow = if (isLink) TextOverflow.Ellipsis else TextOverflow.Clip
                )
            }
        }
        if (canShowButtons) {
            Row(
                modifier = Modifier.width(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall)
            ) {
                CustomIconButton(
                    action = onDeleteClick,
                    imageVector = Icons.Outlined.Delete,
                    modifier = Modifier.size(48.dp)
                )
                CustomIconButton(
                    action = onEditClick,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}