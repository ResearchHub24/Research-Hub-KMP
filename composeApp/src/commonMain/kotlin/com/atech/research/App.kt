package com.atech.research

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.navigation.compose.rememberNavController
import com.atech.research.ui.navigation.ResearchHubNavigation
import com.atech.research.ui.navigation.ResearchNavigationGraph
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.utils.Prefs
import kotlinx.coroutines.flow.map
import org.jetbrains.compose.ui.tooling.preview.Preview


val LocalDataStore =
    staticCompositionLocalOf<DataStore<Preferences>> { error("No DataStore provided") }

@Composable
@Preview
fun App(
    pref: DataStore<Preferences>
) {
    ResearchHubTheme {
        CompositionLocalProvider(LocalDataStore provides pref) {
            val setUpDone by pref.data.map {
                it[booleanPreferencesKey(Prefs.SET_PASSWORD_DONE.key)] ?: false
            }.collectAsState(initial = false)
            val navController = rememberNavController()
            ResearchNavigationGraph(
                modifier = Modifier,
                navHostController = navController,
                startDestination = if (setUpDone) ResearchHubNavigation.MainScreen else ResearchHubNavigation.LogInScreen,
            )
        }
    }
}