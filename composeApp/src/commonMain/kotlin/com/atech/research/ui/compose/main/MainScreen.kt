package com.atech.research.ui.compose.main

//import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
//import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
//import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
//import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Hone", Icons.Default.Home),
    SEARCH("Search", Icons.Default.Search),
    PROFILE("Profile", Icons.Default.Person),
    SETTINGS("Settings", Icons.Default.Settings),
}


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun Container(modifier: Modifier = Modifier) {
    val item = rememberListDetailPaneScaffoldNavigator<Any>()
    ListDetailPaneScaffold(
        directive = item.scaffoldDirective,
        value = item.scaffoldValue,
        listPane = {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(100) { text ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                item.navigateTo(
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
        },
        detailPane = {
            val content = item.currentDestination?.content?.toString() ?: "No content"
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text("Detail: $content")
            }
        }
    )
}

//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Composable
//fun MainScreen(modifier: Modifier = Modifier) {
//    val windowSizeClass = calculateWindowSizeClass()
//    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
//
//    val customNavSuitType = when (windowSizeClass.widthSizeClass) {
//        WindowWidthSizeClass.Compact -> NavigationSuiteType.NavigationBar
//        else -> {
//            NavigationSuiteType.NavigationDrawer
//        }
//    }
//    NavigationSuiteScaffold(
//        layoutType = customNavSuitType,
//        navigationSuiteItems = {
//            AppDestinations.entries.forEach { destination ->
//                item(
//                    icon = {
//                        Icon(
//                            imageVector = destination.icon,
//                            contentDescription = destination.label
//                        )
//                    },
//                    label = {
//                        Text(destination.label)
//                    },
//                    selected = destination == currentDestination,
//                    onClick = {
//                        currentDestination = destination
//                    }
//                )
//            }
//        }
//    ) {}
//}