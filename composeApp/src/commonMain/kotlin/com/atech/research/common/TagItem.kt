package com.atech.research.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.ui.theme.spacing


@Composable
fun TagItem(
    modifier: Modifier = Modifier,
    tag: TagModel,
    isSelected: Boolean = false,
    onItemClicked: (Boolean) -> Unit = {},
    isDeleteEnable: Boolean = false,
    onDeleteTag: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onItemClicked(!isSelected)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isSelected, onCheckedChange = {
                    onItemClicked(it)
                }, modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
            Text(
                text = tag.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterVertically),
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
        if (isDeleteEnable) {
            IconButton(
                modifier = Modifier,
                onClick = onDeleteTag
            ) {
                Icon(imageVector = Icons.Outlined.Remove, contentDescription = null)
            }
        }
    }
}
