package com.atech.research.ui.compose.main.login.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.atech.research.common.MainContainer
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.isAndroid
import org.jetbrains.compose.resources.painterResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
) {
    MainContainer(
        appBarColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->

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
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val viewModel = getViewModel()
                LoginScreenType(
                    viewModel = viewModel,
                    onEvent = viewModel::onLoginEvent,
                    onLogInDone = { }
                )
            }
        }
    }
}