package com.atech.research.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

enum class ResearchHubRoutes(
    val route: String
) {
    LOGIN("login"),
    HOME("home")
}

sealed class ResearchHubNavigation(
    val route: String
) {
    data object LogInScreen : ResearchHubNavigation(ResearchHubRoutes.LOGIN.route)
    data object MainScreen : ResearchHubNavigation(ResearchHubRoutes.HOME.route)
}


@Composable
fun ResearchNavigationGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: ResearchHubNavigation = ResearchHubNavigation.LogInScreen,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination.route,
    ) {
        logInScreenGraph(navHostController)
        mainScreenGraph(navHostController)
    }
}




