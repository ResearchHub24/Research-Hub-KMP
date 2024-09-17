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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.atech.research.LocalDataStore
import com.atech.research.common.MainContainer
import com.atech.research.ui.navigation.MainScreenScreenRoutes
import com.atech.research.ui.navigation.SetUpScreenArgs
import com.atech.research.ui.theme.spacing
import com.atech.research.utils.Prefs
import com.atech.research.utils.isAndroid
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import researchhub.composeapp.generated.resources.Res
import researchhub.composeapp.generated.resources.app_logo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier, navController: NavController
) {
    val pref = LocalDataStore.current
    val uid by pref.data.map {
        it[stringPreferencesKey(Prefs.USER_ID.key)] ?: ""
    }.collectAsState(initial = "")
    LaunchedEffect(uid) {
        if (uid.isNotEmpty() && isAndroid()) {
            navigateToSetupScreen(navController, uid)
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
                val dataStore = LocalDataStore.current
                val viewModel = getViewModel()
                LoginScreenType(viewModel = viewModel,
                    onEvent = viewModel::onLoginEvent,
                    onLogInDone = {
                        scope.launch {
                            dataStore.edit { pref ->
                                pref[stringPreferencesKey(Prefs.USER_ID.key)] = it
                                if (!isAndroid()) {
                                    pref[booleanPreferencesKey(Prefs.SET_PASSWORD_DONE.key)] = true
                                    navigateToHome(navController)
                                    return@edit
                                }
                                navigateToSetupScreen(navController, it)
                            }
                        }
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