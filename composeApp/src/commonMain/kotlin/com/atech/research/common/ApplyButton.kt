package com.atech.research.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.atech.research.ui.theme.spacing

/**
 * Apply button
 * This is a button that is used to apply changes
 *
 * @param text Text to display
 * @param modifier Modifier
 * @param canFillWidth Boolean value to determine if the button should fill the width
 * @param enable Boolean value to determine if the button should be enabled
 * @param horizontalPadding Dp value to determine the horizontal padding
 * @param action () -> Unit action to perform when the button is clicked
 */
@Composable
fun ApplyButton(
    text: String,
    modifier: Modifier = Modifier,
    canFillWidth: Boolean = true,
    enable: Boolean = true,
    horizontalPadding: Dp = MaterialTheme.spacing.default,
    action: () -> Unit
) {
    TextButton(
        modifier = modifier
            .padding(horizontal = horizontalPadding)
            .let { if (canFillWidth) it.fillMaxWidth() else it },
        onClick = action,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(MaterialTheme.spacing.medium),
        enabled = enable
    ) {
        Text(
            modifier = Modifier.padding(MaterialTheme.spacing.medium), text = text
        )
    }
}