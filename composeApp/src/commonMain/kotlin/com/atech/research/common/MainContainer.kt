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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.utils.Platform
import com.atech.research.utils.getPlatformName

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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    enableTopBar: Boolean = getPlatformName() == Platform.ANDROID,
    title: String = "",
    appBarColor: Color = MaterialTheme.colorScheme.background,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavigationClick: (() -> Unit)? = null,
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackBarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit = { }
) {
    val topAppBar: @Composable () -> Unit = if (enableTopBar) {
        {
            Toolbar(
                title = title,
                scrollBehavior = scrollBehavior,
                onNavigationClick = onNavigationClick,
                actions = actions,
                color = appBarColor
            )
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
        content(it)
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