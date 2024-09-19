package com.atech.research.ui.compose.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.atech.research.LocalDataStore
import com.atech.research.core.model.UserType
import com.atech.research.utils.BackHandler
import com.atech.research.utils.PreferenceUtils
import com.atech.research.utils.Prefs

enum class TeacherAppDestinations(
    val label: String,
    val icon: ImageVector,
    val forTeacher: Boolean = false
) {
    Home("Home", Icons.Outlined.Dashboard, true),
    Profile("Profile", Icons.Outlined.AccountCircle, true),

}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun Container(modifier: Modifier = Modifier) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(100) { text ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    navigator.navigateTo(
                                        pane = ListDetailPaneScaffoldRole.Detail,
                                        content = text
                                    )
                                }
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Item $text")
                        }
                    }
                }
            }
        },
        detailPane = {
            val content = navigator.currentDestination?.content?.toString() ?: "No content"
            AnimatedPane {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text("Detail: $content")
                }
            }
        }
    )
}

@Composable
fun MainScreen() {
    val isTeacher = IsUser()
    var currentDestination by rememberSaveable { mutableStateOf(TeacherAppDestinations.Home) }
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customNavSuiteType = with(adaptiveInfo) {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }
    NavigationSuiteScaffold(
        layoutType = customNavSuiteType,
        navigationSuiteItems = {
            TeacherAppDestinations.entries.filter { it.forTeacher == isTeacher }.forEach { item ->
                navItemEntry(
                    item = item,
                    selected = item == currentDestination,
                    onClick = {
                        currentDestination = item
                    }
                )
            }
        }
    ) {
        when (currentDestination) {
            TeacherAppDestinations.Home -> Container()
            TeacherAppDestinations.Profile -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Profile")
                }
            }
        }
    }
}

@Composable
private fun IsUser() =
    PreferenceUtils.builder(LocalDataStore.current)
        .build()
        .getStringPrefAsState(Prefs.USER_TYPE.name).value == UserType.TEACHER.name


fun NavigationSuiteScope.navItemEntry(
    modifier: Modifier = Modifier,
    item: TeacherAppDestinations,
    selected: Boolean,
    onClick: () -> Unit
) {
    item(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        label = {
            Text(item.label)
        },
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label
            )
        },
        alwaysShowLabel = false
    )
}