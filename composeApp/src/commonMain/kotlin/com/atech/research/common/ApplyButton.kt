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