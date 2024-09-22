package com.atech.research.ui.compose.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.window.core.layout.WindowWidthSizeClass
import com.atech.research.LocalDataStore
import com.atech.research.core.ktor.model.UserType
import com.atech.research.ui.compose.teacher.home.compose.HomeScreen
import com.atech.research.utils.Prefs

enum class TeacherAppDestinations(
    val label: String,
    val icon: ImageVector,
    val forTeacher: Boolean = false
) {
    Home("Home", Icons.Outlined.Dashboard, true),
    Profile("Profile", Icons.Outlined.AccountCircle, true),

}


@Composable
fun MainScreen() {
    val isTeacher = IsUser()
    var currentDestination by rememberSaveable { mutableStateOf(TeacherAppDestinations.Home) }
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customNavSuiteType = with(adaptiveInfo) {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationSuiteType.NavigationRail
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
            TeacherAppDestinations.Home -> HomeScreen()

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
    LocalDataStore.current.getString(Prefs.USER_TYPE.name) == UserType.TEACHER.name


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