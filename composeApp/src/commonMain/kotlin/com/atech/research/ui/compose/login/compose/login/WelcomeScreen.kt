package com.atech.research.ui.compose.login.compose.login

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.atech.research.LocalDataStore
import com.atech.research.common.MainContainer
import com.atech.research.ui.navigation.MainScreenScreenRoutes
import com.atech.research.ui.navigation.SetUpScreenArgs
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.Prefs
import com.atech.research.utils.isAndroid
import org.jetbrains.compose.resources.painterResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo

/**
 * Welcome screen
 * This is the screen that displays the welcome screen.
 * @param modifier The modifier to be applied to the screen
 * @param navController The navigation controller
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier, navController: NavController
) {
    val pref = LocalDataStore.current
    val uid = pref.getString(Prefs.USER_ID.name)
    LaunchedEffect(uid) {
        if (uid.isNotEmpty() && isAndroid()) {
            navigateToSetupScreen(navController, uid)
        }
        if (uid.isNotEmpty() && !isAndroid()) {
            navigateToHome(navController)
        }
    }
    val scope = rememberCoroutineScope()
    MainContainer(
        appBarColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxWidth().fillMaxHeight()
        ) {
            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary).fillMaxWidth()
                    .fillMaxHeight(.5f)
            ) {
                Image(
                    painterResource(Res.drawable.app_logo),
                    contentDescription = "Logo",
                    modifier = modifier.fillMaxWidth().fillMaxHeight()
                )
            }
            Column(
                modifier = modifier.fillMaxSize().padding(MaterialTheme.spacing.medium),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val viewModel = getViewModel()
                LoginScreenType(viewModel = viewModel,
                    onEvent = viewModel::onLoginEvent,
                    onLogInDone = { loginResponse ->
                        pref.saveString(Prefs.USER_ID.name, loginResponse.uid)
                        pref.saveString(Prefs.USER_NAME.name, loginResponse.name)
                        pref.saveString(Prefs.USER_EMAIL.name, loginResponse.email)
                        pref.saveString(Prefs.USER_PROFILE_URL.name, loginResponse.photoUrl ?: "")
                        if (!isAndroid()) {
                            pref.saveString(Prefs.USER_TYPE.name, loginResponse.userType)
                            pref.saveBoolean(Prefs.SET_PASSWORD_DONE.name, true)
                            navigateToHome(navController)
                            return@LoginScreenType
                        }
                        navigateToSetupScreen(navController, loginResponse.uid)
                    })
            }
        }
    }
}

/**
 * Navigate to setup screen
 * This is the function that navigates to the setup screen.
 * @param navController The navigation controller
 * @param uid The user id
 */
private fun navigateToSetupScreen(navController: NavController, uid: String) {
    navController.navigate(SetUpScreenArgs(uid)) {
        popUpTo(navController.graph.startDestinationId)
    }
}

/**
 * Navigate to home
 * This is the function that navigates to the home screen.
 * @param navController The navigation controller
 */
fun navigateToHome(navController: NavController) {
    navController.navigate(MainScreenScreenRoutes.HomeScreen.route) {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }
    }
}