package com.atech.research.ui.compose.forum.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.atech.research.common.AsyncImage

/**
 * Forum item user
 * This is the user message item in the forum.
 * @param message The message to be displayed
 * @param time The time the message was sent
 * @param modifier The modifier to be applied to the item
 */
@Composable
fun ForumItemUser(
    message: String,
    time: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 4.dp
                    )
                )
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(12.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = time,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp)
            )
        }
    }
}

/**
 * Forum item response
 * This is the response message item in the forum.
 * @param message The message to be displayed
 * @param time The time the message was sent
 * @param url The url of the user image
 * @param modifier The modifier to be applied to the item
 */
@Composable
fun ForumItemResponse(
    message: String,
    time: String,
    url: String,
    modifier: Modifier = Modifier
) {
    Surface {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    url = url,
                    isLoadCircular = true,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Message Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp)
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp)
                )
            }
        }
    }
}