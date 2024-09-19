package com.atech.research.ui.compose.teacher.home.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atech.research.common.MainContainer
import com.atech.research.utils.BackHandler


@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }
    MainContainer(
        modifier = modifier,
        title = "Home",
        enableTopBar = true
    ){paddingValue->
        ListDetailPaneScaffold(
            modifier = Modifier.padding(paddingValue),
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
}