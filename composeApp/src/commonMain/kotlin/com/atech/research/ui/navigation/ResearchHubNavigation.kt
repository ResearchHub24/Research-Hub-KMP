package com.atech.research.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.atech.research.ui.compose.login.compose.verify.compose.VerifyScreen
import com.atech.research.utils.fadeThroughComposable

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
    HOME("home"),
    VERIFY("verify")
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

    /**
     * Verify screen
     */
    data object VerifyScreen : ResearchHubNavigation(ResearchHubRoutes.VERIFY.route)
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
        fadeThroughComposable(
            route = ResearchHubRoutes.VERIFY.route,
        ) {
            VerifyScreen(
                navController = navHostController
            )
        }
    }
}




