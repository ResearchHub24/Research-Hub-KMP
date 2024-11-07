package com.atech.research.ui.compose.teacher.application.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.atech.research.common.AppCustomAlertDialog
import com.atech.research.common.ApplyButton
import com.atech.research.common.AsyncImage
import com.atech.research.common.EditTextEnhance
import com.atech.research.common.ExpandableCard
import com.atech.research.core.ktor.model.Action
import com.atech.research.core.ktor.model.AnswerModel
import com.atech.research.core.ktor.model.ApplicationModel
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.convertToDateFormat

/**
 * Application Item
 *
 * @param modifier Modifier
 * @param model Application model
 * @param onClick Click action
 * @param onViewProfileClick View profile click action
 * @param onActionClick Action click action
 */
@Composable
fun ApplicationItem(
    modifier: Modifier = Modifier,
    model: ApplicationModel,
    onClick: () -> Unit = {},
    onViewProfileClick: () -> Unit = {},
    onActionClick: (Action) -> Unit = { _ -> },
) {
    var isDialogVisible by rememberSaveable { mutableStateOf(false) }

    AnimatedVisibility(isDialogVisible) {
        ActionDialog(
            action = model.action,
            onActionClick = onActionClick,
            onDismissRequest = {
                isDialogVisible = false
            }
        )
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
            colors = CardDefaults.cardColors(
                containerColor = when (model.action) {
                    Action.SELECTED -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                    Action.REJECTED -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                    Action.PENDING -> MaterialTheme.colorScheme.surface
                }
            )
        ) {
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.medium)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            isLoadCircular = true,
                            url = model.userProfile
                        )
                        Spacer(Modifier.width(MaterialTheme.spacing.medium))
                        Column {
                            Text(
                                text = model.userName,
                                maxLines = 1,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(MaterialTheme.spacing.small)
                                    .basicMarquee()
                            )
                            Text(
                                text = model.userEmail,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(MaterialTheme.spacing.small)
                                    .basicMarquee()
                            )
                        }
                    }

                    // Status Icon
                    when (model.action) {
                        Action.SELECTED -> Icon(
                            imageVector = Icons.Rounded.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )

                        Action.REJECTED -> Icon(
                            imageVector = Icons.Rounded.Cancel,
                            contentDescription = "Rejected",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(24.dp)
                        )

                        Action.PENDING -> Icon(
                            imageVector = Icons.Rounded.Schedule,
                            contentDescription = "Pending",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(Modifier.height(MaterialTheme.spacing.medium))
                HorizontalDivider()

                // Research Title
                Text(
                    text = model.researchTitle,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
                )

                ExpandableCard(title = "Response") {
                    model.answers.forEach {
                        Spacer(Modifier.height(MaterialTheme.spacing.medium))
                        QuestionsItem(model = it)
                    }
                }

                Spacer(Modifier.height(MaterialTheme.spacing.medium))
                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MaterialTheme.spacing.small),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Applied on ${model.created.convertToDateFormat()}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = when (model.action) {
                            Action.SELECTED -> "Selected"
                            Action.REJECTED -> "Rejected"
                            Action.PENDING -> "Pending Review"
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = when (model.action) {
                            Action.SELECTED -> MaterialTheme.colorScheme.primary
                            Action.REJECTED -> MaterialTheme.colorScheme.error
                            Action.PENDING -> MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }

                Spacer(Modifier.height(MaterialTheme.spacing.medium))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                ) {
                    ApplyButton(
                        modifier = Modifier.weight(.5f),
                        text = if (model.action == Action.PENDING) "Take Action" else "Change Status",
                        enable = true,
                        action = { isDialogVisible = true }
                    )
                    ApplyButton(
                        modifier = Modifier.weight(.5f),
                        text = "View Profile",
                        action = onViewProfileClick
                    )
                }
            }
        }
    }
}

/**
 * Questions Item
 *
 * @param modifier Modifier
 * @param model Answer model
 */
@Composable
fun QuestionsItem(
    modifier: Modifier = Modifier,
    model: AnswerModel
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        EditTextEnhance(
            modifier = Modifier.fillMaxWidth(),
            value = model.question ?: "",
            onValueChange = {},
            placeholder = "Question",
            enable = false,
            trailingIcon = null,
            colors = questionItemTextFieldColor().copy(
                disabledContainerColor = TextFieldDefaults.colors().unfocusedContainerColor,
                disabledLabelColor = TextFieldDefaults.colors().unfocusedLabelColor,
                disabledIndicatorColor = TextFieldDefaults.colors().unfocusedIndicatorColor
            )
        )

        EditTextEnhance(
            modifier = Modifier.fillMaxWidth(),
            value = model.answer ?: "",
            onValueChange = {},
            placeholder = "Answer",
            enable = false,
            trailingIcon = null,
            colors = questionItemTextFieldColor()
        )
    }
}

/**
 * Question Item Text Field Color
 *
 * @return TextFieldDefaults colors
 */
@Composable
private fun questionItemTextFieldColor() = TextFieldDefaults.colors(
    disabledContainerColor = TextFieldDefaults.colors().focusedContainerColor,
    disabledLabelColor = TextFieldDefaults.colors().focusedLabelColor,
    disabledTextColor = TextFieldDefaults.colors().focusedTextColor,
    disabledIndicatorColor = TextFieldDefaults.colors().focusedIndicatorColor
)


/**
 * Action Dialog
 *
 * @param modifier Modifier
 * @param action Action
 * @param onActionClick Action click action
 * @param onDismissRequest Dismiss request action
 */
@Composable
private fun ActionDialog(
    modifier: Modifier = Modifier,
    action: Action = Action.PENDING,
    onActionClick: (Action) -> Unit = { _ -> },
    onDismissRequest: () -> Unit = {},
) {
    var clickedAction by rememberSaveable { mutableStateOf(action) }
    AppCustomAlertDialog(
        modifier = modifier
            .fillMaxWidth(),
        onDismissRequest = onDismissRequest
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = AlertDialogDefaults.containerColor,
            ), shape = AlertDialogDefaults.shape, elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = AlertDialogDefaults.TonalElevation
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.medium)
                    .padding(vertical = MaterialTheme.spacing.large)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Action",
                    color = AlertDialogDefaults.titleContentColor
                )
                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.medium))
                Action.entries.forEach { it ->
                    Row(
                        modifier = Modifier
                            .padding(
                                MaterialTheme.spacing.medium,
                            )
                            .fillMaxWidth()
                            .clickable {
                                clickedAction = it
                            },
                    ) {
                        RadioButton(
                            selected = (it.name == clickedAction.name),
                            onClick = null
                        )
                        Text(
                            text = it.name.lowercase().replaceFirstChar { it.uppercaseChar() },
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = MaterialTheme.spacing.large),
                            color = AlertDialogDefaults.textContentColor
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(
                        modifier = Modifier.weight(.5f),
                        onClick = {
                            onActionClick.invoke(clickedAction)
                            onDismissRequest.invoke()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = AlertDialogDefaults.iconContentColor
                        )
                    }
                    IconButton(
                        modifier = Modifier.weight(.5f),
                        onClick = onDismissRequest
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Cancel,
                            contentDescription = null,
                            tint = AlertDialogDefaults.iconContentColor
                        )
                    }
                }
            }
        }
    }
}
