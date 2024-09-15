package com.atech.research.ui.compose.main.login.compose.setup

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.atech.research.common.MainContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUpScreen(modifier: Modifier = Modifier) {
    MainContainer(
        title = "Set Up",
    ) { contentPadding ->
        Text(
            text = "Set Up",
            modifier = modifier.padding(contentPadding)
        )
    }
}
