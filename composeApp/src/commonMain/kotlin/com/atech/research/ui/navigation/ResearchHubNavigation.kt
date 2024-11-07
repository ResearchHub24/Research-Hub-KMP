package com.atech.research.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

/**
 * Research hub routes
 *
 * @property route String
 * @constructor Create empty Research hub routes
 */
enum class ResearchHubRoutes(
    val route: String
) {
    LOGIN("login"),
    HOME("home")
}

/**
 * Research hub navigation
 *
 * @property route String
 * @constructor Create empty Research hub navigation
 */
sealed class ResearchHubNavigation(
    val route: String
) {
    /**
     * Log in screen
     */
    data object LogInScreen : ResearchHubNavigation(ResearchHubRoutes.LOGIN.route)
    /**
     * Main screen
     */
    data object MainScreen : ResearchHubNavigation(ResearchHubRoutes.HOME.route)
}


/**
 * Research navigation graph
 *
 * @param modifier Modifier
 * @param navHostController NavHostController
 * @param startDestination ResearchHubNavigation
 * @see NavGraphBuilder.logInScreenGraph
 * @see NavGraphBuilder.mainScreenGraph
 */
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




