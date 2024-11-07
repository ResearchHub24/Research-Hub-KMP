package com.atech.research.ui.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.atech.research.ui.compose.login.compose.login.WelcomeScreen
import com.atech.research.ui.compose.login.compose.setup.SetUpScreenEvents
import com.atech.research.ui.compose.login.compose.setup.SetUpViewModel
import com.atech.research.ui.compose.login.compose.setup.compose.SetUpScreen
import com.atech.research.utils.fadeThroughComposable
import com.atech.research.utils.fadeThroughComposableEnh
import com.atech.research.utils.koinViewModel
import kotlinx.serialization.Serializable

/**
 * Log in screen routes
 *
 * @property route String
 * @constructor Create empty Log in screen routes
 */
sealed class LogInScreenRoutes(
    val route: String
) {
    /**
     * Log in screen
     */
    data object LogInScreen : LogInScreenRoutes("log_in_screen")
}


/**
 * Set up screen args
 *
 * @property uid String
 * @constructor Create empty Set up screen args
 */
@Serializable
data class SetUpScreenArgs(
    val uid: String
)

/**
 * Log in screen graph
 *
 * @param navController NavController
 * @see SetUpScreen
 */
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
//            val viewModel = route.sharedViewModel<SetUpViewModel>(navController)
            val viewModel = koinViewModel<SetUpViewModel>()
            var isDataSet by rememberSaveable { mutableStateOf(false) }
            if (!isDataSet) {
                viewModel.onEvent(
                    SetUpScreenEvents.SetUid(
                        route.arguments?.getString("uid") ?: return@fadeThroughComposableEnh
                    )
                )
                isDataSet = true
            }
            val passWord by viewModel.password
            val userType by viewModel.userType
            val isPasswordValid by viewModel.isPasswordValid
            val user by viewModel.user
            SetUpScreen(
                navController = navController,
                user = user,
                passWord = passWord,
                userType = userType,
                isPasswordValid = isPasswordValid,
                onEvent = viewModel::onEvent
            )
        }
    }
}
