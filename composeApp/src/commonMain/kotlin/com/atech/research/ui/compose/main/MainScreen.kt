package com.atech.research.ui.compose.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AppRegistration
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Groups2
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.atech.research.LocalDataStore
import com.atech.research.core.ktor.model.UserType
import com.atech.research.ui.compose.profile.compose.ProfileScreen
import com.atech.research.ui.compose.teacher.home.compose.HomeScreen
import com.atech.research.utils.Prefs

enum class TeacherAppDestinations(
    val label: String, val icon: ImageVector, val forTeacher: Boolean = false
) {
    Home("Home", Icons.Rounded.Dashboard, true),
    Faculty(
        "Faculty",
        Icons.Rounded.Groups2,
        false
    ),
    FillResearch("Fill Research", Icons.Rounded.AppRegistration, false),
    Profile(
        "Profile",
        Icons.Rounded.AccountCircle,
        true
    )
}


@Composable
fun MainScreen(
    navHostController: NavController
) {
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
    NavigationSuiteScaffold(navigationSuiteColors = NavigationSuiteDefaults.colors(
        navigationRailContainerColor = BottomAppBarDefaults.containerColor
    ), layoutType = customNavSuiteType, navigationSuiteItems = {
        setAccordingToUser(isTeacher = isTeacher, teacherComposable = {
            TeacherAppDestinations.entries.filter { it.forTeacher == isTeacher }
        }, studentComposable = {
            TeacherAppDestinations.entries
        }).forEach { item ->
            navItemEntry(item = item, selected = item == currentDestination, onClick = {
                currentDestination = item
            })
        }
    }) {
        when (currentDestination) {
            TeacherAppDestinations.Home -> SetComposableAccordingToUser(teacherComposable = { HomeScreen() },
                studentComposable = {})

            TeacherAppDestinations.Profile -> ProfileScreen(navHostController = navHostController)
            TeacherAppDestinations.Faculty -> Column {
                Text("Faculty")
            }

            TeacherAppDestinations.FillResearch -> Column {
                Text("Fill Research")
            }
        }
    }
}


@Composable
fun SetComposableAccordingToUser(
    isTeacher: Boolean = IsUser(),
    teacherComposable: @Composable () -> Unit = {},
    studentComposable: @Composable () -> Unit = {}
) = if (isTeacher) teacherComposable()
else studentComposable()

fun <T> setAccordingToUser(
    isTeacher: Boolean, teacherComposable: () -> T, studentComposable: () -> T
) = if (isTeacher) teacherComposable()
else studentComposable()


@Composable
fun IsUser() = LocalDataStore.current.getString(Prefs.USER_TYPE.name) == UserType.TEACHER.name


fun NavigationSuiteScope.navItemEntry(
    modifier: Modifier = Modifier,
    item: TeacherAppDestinations,
    selected: Boolean,
    onClick: () -> Unit
) {
    item(modifier = modifier, selected = selected, onClick = onClick, label = {
//        Text(item.label)
    }, icon = {
        Icon(
            imageVector = item.icon, contentDescription = item.label
        )
    }, alwaysShowLabel = false
    )
}