package com.atech.research

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.navigation.compose.rememberNavController
import com.atech.research.ui.navigation.ResearchHubNavigation
import com.atech.research.ui.navigation.ResearchNavigationGraph
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.utils.PrefManager
import org.jetbrains.compose.ui.tooling.preview.Preview


val LocalDataStore =
    staticCompositionLocalOf<PrefManager> { error("No DataStore provided") }

@Composable
@Preview
fun App(
    pref: PrefManager
) {
    ResearchHubTheme {
        CompositionLocalProvider(LocalDataStore provides pref) {
            val navController = rememberNavController()
            ResearchNavigationGraph(
                modifier = Modifier,
                navHostController = navController,
                startDestination = ResearchHubNavigation.LogInScreen,
            )
        }
    }
}