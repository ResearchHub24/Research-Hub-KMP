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
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.isAndroid
import com.atech.research.utils.researchHubLog
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController
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
                    onLogInDone = { res,name ->
                        val uid1 = res.split("$").first()
                            pref.saveString(Prefs.USER_ID.name, uid1)
                            pref.saveString(Prefs.USER_NAME.name, name)
                            if (!isAndroid()) {
                                val userTypeId = res.split("$").last()
                                pref.saveString(Prefs.USER_TYPE.name, userTypeId)
                                pref.saveBoolean(Prefs.SET_PASSWORD_DONE.name, true)
                                navigateToHome(navController)
                                return@LoginScreenType
                            }
                            navigateToSetupScreen(navController, uid1)

                    })
            }
        }
    }
}

private fun navigateToSetupScreen(navController: NavController, uid: String) {
    navController.navigate(SetUpScreenArgs(uid)) {
        popUpTo(navController.graph.startDestinationId)
    }
}

fun navigateToHome(navController: NavController) {
    navController.navigate(MainScreenScreenRoutes.HomeScreen.route) {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }
    }
}