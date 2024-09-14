package com.atech.research.ui.compose.main.login.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atech.research.common.MainContainer
import org.jetbrains.compose.resources.painterResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo
import researchhub.composeapp.generated.resources.compose_multiplatform

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier
) {
    MainContainer (
        appBarColor = MaterialTheme.colorScheme.primary
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
            ) {
                Image(
                    painterResource(Res.drawable.app_logo),
                    contentDescription = "Logo",
                    modifier = modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
            }
        }
    }
}