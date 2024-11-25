package com.atech.research.ui.compose.login.compose.verify.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.atech.research.common.ProgressBar
import com.atech.research.ui.compose.login.compose.verify.VerifyScreenViewModel
import com.atech.research.ui.navigation.MainScreenScreenRoutes
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.DataState
import com.atech.research.utils.koinViewModel
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.img_verify

@Composable
fun VerifyScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val viewModel = koinViewModel<VerifyScreenViewModel>()
    val isVerifiedDataState by viewModel.isVerified

    if (isVerifiedDataState is DataState.Loading) {
        ProgressBar(PaddingValues())
        return
    }
    if (isVerifiedDataState is DataState.Error) {
//        TODO: Handle Error
        return
    }
    if (isVerifiedDataState is DataState.Success) {
        val isVerified = (isVerifiedDataState as DataState.Success<Boolean>).data
        if (!isVerified)
            Column(
                modifier = modifier.fillMaxSize()
                    .padding(MaterialTheme.spacing.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = org.jetbrains.compose.resources.painterResource(Res.drawable.img_verify),
                    contentDescription = "Add Password",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Text(
                    text = "To maintain the integrity of the platform, we need to verify your account.This will help us to ensure that only teachers are using the platform.",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                Text(
                    text = "This process will take a few hours. We will notify you once your account is verified.",
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        else navigateToHome(navController)
    }
}


/**
 * Navigate to home
 * This is the function that navigates to the home screen.
 * @param navController The navigation controller
 */
private fun navigateToHome(navController: NavController) {
    navController.navigate(MainScreenScreenRoutes.HomeScreen.route) {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }
    }
}