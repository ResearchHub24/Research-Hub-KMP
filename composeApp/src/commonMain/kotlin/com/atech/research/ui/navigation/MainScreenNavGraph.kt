package com.atech.research.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.atech.research.ui.compose.main.MainScreen
import com.atech.research.utils.DeepLink
import com.atech.research.utils.fadeThroughComposable

sealed class MainScreenScreenRoutes(
    val route: String
) {
    data object HomeScreen : MainScreenScreenRoutes("home_screen")
}

fun NavGraphBuilder.mainScreenGraph(navController: NavController) {
    navigation(
        startDestination = MainScreenScreenRoutes.HomeScreen.route,
        route = ResearchHubNavigation.MainScreen.route
    ) {
        fadeThroughComposable(
            route = MainScreenScreenRoutes.HomeScreen.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLink.OpenResearch().route
                    action = "android.intent.action.VIEW"
                }
            )
        ) {
            MainScreen(
                navHostController = navController
            )
        }
    }
}