package com.atech.research.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.atech.research.ui.compose.main.MainScreen
import com.atech.research.utils.DeepLink
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.fadeThroughComposable
import com.atech.research.utils.researchHubLog

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
            arguments = listOf(navArgument("researchPath") {
                nullable = true
                defaultValue = null
                type = NavType.StringType
            }),
            deepLinks = listOf(navDeepLink {
                uriPattern = DeepLink.OpenResearch().route
                action = "android.intent.action.VIEW"
            }
            )
        ) {
            val researchPath = it.arguments?.getString("researchPath")
//            researchHubLog(
//                ResearchLogLevel.INFO,
//                researchPath ?: ""
//            )
//            researchHubLog(
//                ResearchLogLevel.INFO,
//                researchPath ?: ""
//            )
            MainScreen(
                navHostController = navController,
                researchPath = researchPath
            )
        }
    }
}