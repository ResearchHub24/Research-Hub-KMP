package com.atech.research.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.DialogProperties

/**
 * App custom alert dialog with custom content
 *
 * @param modifier Modifier
 * @param onDismissRequest () -> Unit
 * @param content @Composable () -> Unit
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCustomAlertDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,

    ) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        properties = DialogProperties(),
        content = content
    )
}

/**
 * App alert dialog with confirmation and dismiss button
 *
 * @param dialogTitle String
 * @param dialogText String
 * @param icon ImageVector
 * @param onDismissRequest () -> Unit
 * @param onConfirmation () -> Unit
 */
@Composable
fun AppAlertDialog(
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
