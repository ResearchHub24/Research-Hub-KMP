package com.atech.research.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.atech.research.ui.theme.ResearchHubTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(modifier: Modifier = Modifier) {
    var isLoading by remember { mutableStateOf(false) }
    MainContainer(
        modifier = modifier,
        title = "Set Up",
    ) { contentPadding ->
        AnimatedVisibility(isLoading) {
            ProgressBar(paddingValues = contentPadding)
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun TestPreview() {
    ResearchHubTheme(
        isDark = true
    ) {
        TestScreen()
    }
}