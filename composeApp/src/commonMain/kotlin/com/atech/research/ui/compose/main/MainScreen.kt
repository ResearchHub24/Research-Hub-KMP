package com.atech.research.ui.compose.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.AppRegistration
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Groups2
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.atech.research.LocalDataStore
import com.atech.research.core.ktor.model.UserType
import com.atech.research.ui.compose.forum.compose.ForumScreen
import com.atech.research.ui.compose.profile.compose.ProfileScreen
import com.atech.research.ui.compose.student.application.compose.ApplicationScreen
import com.atech.research.ui.compose.student.faculties.compose.FacultiesScreen
import com.atech.research.ui.compose.student.home.compose.StudentHomeScreen
import com.atech.research.ui.compose.teacher.home.compose.HomeScreen
import com.atech.research.utils.Prefs

/**
 * Teacher app destinations
 * This is the teacher app destinations.
 * @property label Label of the destination
 * @property icon  Icon of the destination
 * @property forTeacher Whether the destination is for teacher or not
 * @constructor Create empty Teacher app destinations
 */
enum class AppDestinations(
    val label: String, val icon: ImageVector, val forTeacher: Boolean = false
) {
    Home("Home", Icons.Rounded.Dashboard, true),
    Faculty(
        "Faculty",
        Icons.Rounded.Groups2,
        false
    ),
    FillResearch("Fill Research", Icons.Rounded.AppRegistration, false),
    Forum(
        "Forum",
        Icons.Default.Forum,
        true
    ),
    Profile(
        "Profile",
        Icons.Rounded.AccountCircle,
        true
    )
}


/**
 * Main screen
 * This is the main screen.
 * @param navHostController The nav host controller
 * @param researchPath The research path
 * @see AppDestinations
 */
@Composable
fun MainScreen(
    navHostController: NavController,
    researchPath: String? = null
) {
    val isTeacher = IsUserTeacher()
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.Home) }
    val adaptiveInfo = currentWindowAdaptiveInfo()
    var showNavigation by remember { mutableStateOf(true) }

    val customNavSuiteType = with(adaptiveInfo) {
        when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> when {
                showNavigation -> NavigationSuiteType.NavigationBar
                else -> NavigationSuiteType.None
            }

            WindowWidthSizeClass.MEDIUM -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
                adaptiveInfo
            )

            WindowWidthSizeClass.EXPANDED -> NavigationSuiteType.NavigationRail
            else -> NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }
    }
    NavigationSuiteScaffold(
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationRailContainerColor = BottomAppBarDefaults.containerColor
        ),
        layoutType = customNavSuiteType,
        navigationSuiteItems = {
            setAccordingToUser(isTeacher = isTeacher, teacherComposable = {
                AppDestinations.entries.filter { it.forTeacher == isTeacher }
            }, studentComposable = {
                AppDestinations.entries
            }).forEach { item ->
                navItemEntry(item = item, selected = item == currentDestination, onClick = {
                    currentDestination = item
                })
            }
        }
    ) {
        when (currentDestination) {
            AppDestinations.Home -> SetComposableAccordingToUser(teacherComposable = {
                HomeScreen {
                    showNavigation = it
                }
            },
                studentComposable = {
                    StudentHomeScreen(
                        navHostController = navHostController,
                        researchPath = researchPath
                    ) {
                        showNavigation = it
                    }
                })

            AppDestinations.Profile -> ProfileScreen(
                navHostController = navHostController,
                isTeacher = isTeacher
            )

            AppDestinations.Faculty -> FacultiesScreen {
                showNavigation = it
            }

            AppDestinations.FillResearch -> ApplicationScreen {
                showNavigation = it
            }

            AppDestinations.Forum -> ForumScreen {
                showNavigation = it
            }
        }
    }
}

/**
 * Set composable according to user
 * This is the set composable according to user.
 * @param isTeacher Whether the user is teacher or not
 * @param teacherComposable The teacher composable
 * @param studentComposable The student composable
 * @see IsUserTeacher
 */
@Composable
fun SetComposableAccordingToUser(
    isTeacher: Boolean = IsUserTeacher(),
    teacherComposable: @Composable () -> Unit = {},
    studentComposable: @Composable () -> Unit = {}
) = if (isTeacher) teacherComposable()
else studentComposable()

/**
 * Set according to user
 * This is the set according to user.
 * @param T The type of the set
 * @param isTeacher Whether the user is teacher or not
 * @param teacherComposable The teacher composable
 * @param studentComposable The student composable
 * @return The result of the set
 * @see IsUserTeacher
 */
fun <T> setAccordingToUser(
    isTeacher: Boolean, teacherComposable: () -> T, studentComposable: () -> T
) = if (isTeacher) teacherComposable()
else studentComposable()


/**
 * Is user teacher
 * This is the is user teacher.
 * @return The result of the is user teacher
 */
@Composable
fun IsUserTeacher() =
    LocalDataStore.current.getString(Prefs.USER_TYPE.name) == UserType.TEACHER.name

/**
 * Nav item entry
 * This is the nav item entry.
 * @param modifier The modifier
 * @param item The item
 * @param selected The selected
 * @param onClick The on click
 * @see AppDestinations
 * @see NavigationSuiteScope
 * @see MainScreen
 */
fun NavigationSuiteScope.navItemEntry(
    modifier: Modifier = Modifier,
    item: AppDestinations,
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