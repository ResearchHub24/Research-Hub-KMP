package com.atech.research.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import com.atech.research.ui.compose.main.login.compose.login.WelcomeScreen
import com.atech.research.ui.compose.main.login.compose.setup.SetUpViewModel
import com.atech.research.ui.compose.main.login.compose.setup.compose.SetUpScreen
import com.atech.research.utils.fadeThroughComposable
import com.atech.research.utils.fadeThroughComposableEnh
import com.atech.research.utils.sharedViewModel
import kotlinx.serialization.Serializable

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
    }
}


sealed class LogInScreenRoutes(
    val route: String
) {
    data object LogInScreen : LogInScreenRoutes("log_in_screen")
}


@Serializable
data class SetUpScreenArgs(
    val uid: String
)

fun NavGraphBuilder.logInScreenGraph(
    navController: NavController
) {
    navigation(
        route = ResearchHubNavigation.LogInScreen.route,
        startDestination = LogInScreenRoutes.LogInScreen.route
    ) {
        fadeThroughComposable(
            route = LogInScreenRoutes.LogInScreen.route
        ) {
            WelcomeScreen(
                navController = navController
            )
        }
        fadeThroughComposableEnh<SetUpScreenArgs> { route ->
            val viewModel = route.sharedViewModel<SetUpViewModel>(navController)
            val passWord by viewModel.password
            val userType by viewModel.userType
            val isPasswordValid by viewModel.isPasswordValid
            SetUpScreen(
                navController = navController,
                passWord = passWord,
                userType = userType,
                isPasswordValid = isPasswordValid,
                onEvent = viewModel::onEvent
            )
        }
    }
}

