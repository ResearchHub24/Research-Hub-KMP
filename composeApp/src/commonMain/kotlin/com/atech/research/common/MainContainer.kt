package com.atech.research.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.utils.Platform
import com.atech.research.utils.getPlatformName

/**
 * Toolbar component
 *
 * @param modifier Modifier
 * @param title String title
 * @param onNavigationClick (() -> Unit)? action to perform when the navigation icon is clicked
 * @param actions @Composable RowScope.() -> Unit actions
 * @param scrollBehavior TopAppBarScrollBehavior?
 * @param color Color
 * @see MainContainer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    title: String = "",
    onNavigationClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    color: Color = MaterialTheme.colorScheme.background
) {
    val icon: @Composable () -> Unit = if (onNavigationClick != null) {
        {
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back"
                )
            }
        }
    } else {
        {}
    }

    TopAppBar(
        title = {
            Text(text = title)
        },
        modifier = modifier,
        actions = actions,
        scrollBehavior = scrollBehavior,
        navigationIcon = icon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = color
        )
    )
}

/**
 * Main container component
 * This is the main container for the app that contains the top bar, content, bottom bar, and snackbar
 * It also contains the pull to refresh component
 *
 *
 * @param modifier Modifier
 * @param enableTopBar Boolean value to determine if the top bar should be enabled
 * @param customTopBar (@Composable () -> Unit)? custom top bar
 * @param title String title
 * @param appBarColor Color
 * @param scrollBehavior TopAppBarScrollBehavior?
 * @param state PullToRefreshState
 * @param isRefreshing Boolean value to determine if the content is refreshing
 * @param onRefresh  () -> Unit action to perform when the content is refreshing
 * @param onNavigationClick (() -> Unit)? action to perform when the navigation icon is clicked
 * @param floatingActionButton @Composable () -> Unit floating action button
 * @param actions @Composable RowScope.() -> Unit actions
 * @param bottomBar @Composable () -> Unit bottom bar
 * @param snackBarHost @Composable () -> Unit snackbar host
 * @param content @Composable (PaddingValues) -> Unit content to display
 * @see Toolbar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    enableTopBar: Boolean = getPlatformName() == Platform.ANDROID,
    customTopBar: (@Composable () -> Unit)? = null,
    title: String = "",
    appBarColor: Color = MaterialTheme.colorScheme.background,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    state: PullToRefreshState = rememberPullToRefreshState(),
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    onNavigationClick: (() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackBarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = { }
) {
    val topAppBar: @Composable () -> Unit = if (enableTopBar) {
        if (customTopBar != null) {
            customTopBar
        } else {
            {
                Toolbar(
                    title = title,
                    onNavigationClick = onNavigationClick,
                    actions = actions,
                    scrollBehavior = scrollBehavior,
                    color = appBarColor
                )
            }
        }
    } else {
        {}
    }
    Scaffold(
        modifier = modifier, topBar = topAppBar,
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar,
        snackbarHost = snackBarHost
    ) {
        PullToRefreshBox(
            modifier = Modifier,
            state = state,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh
        ) {
            content(it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContainerPreview() {
    ResearchHubTheme {
        MainContainer(

        )
    }
}