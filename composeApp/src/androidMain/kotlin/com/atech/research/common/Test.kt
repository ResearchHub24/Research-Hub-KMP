package com.atech.research.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.ui.theme.ResearchHubTheme
import com.atech.research.ui.theme.spacing


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun StudentHomeScreen(
    modifier: Modifier = Modifier
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    ListDetailPaneScaffold(
        modifier = modifier,
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            ListScreen()
        },
        detailPane = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListScreen(
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    MainContainer(
        title = "Home",
        customTopBar = {
            Row(
                modifier = Modifier
                    .padding(
                        horizontal =
                        if (!expanded) MaterialTheme.spacing.medium else MaterialTheme.spacing.default
                    )
                    .animateContentSize()
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    inputField = {
                        SearchBarDefaults.InputField(
                            onSearch = { expanded = false },
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                            placeholder = { Text("Search") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            trailingIcon = {
                                Icon(
                                    if (expanded) Icons.Default.FilterAlt else Icons.Default.AccountCircle,
                                    contentDescription = null
                                )
                            },
                            query = "",
                            onQueryChange = { },
                        )
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                ) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        repeat(4) { idx ->
                            val resultText = "Suggestion $idx"
                            ListItem(
                                headlineContent = { Text(resultText) },
                                supportingContent = { Text("Additional info") },
                                leadingContent = {
                                    Icon(
                                        Icons.Filled.Star,
                                        contentDescription = null
                                    )
                                },
                                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                modifier =
                                Modifier
                                    .clickable {
//                                textFieldState.setTextAndPlaceCursorAtEnd(resultText)
                                        expanded = false
                                    }
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        },
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scrollBehavior = scrollBehavior,
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.medium)
                .semantics { traversalIndex = 1f },
            contentPadding = contentPadding
        ) {
            item(key = "welcome") {
                WelcomeSection()
            }
        }

    }
}

@Composable
private fun WelcomeSection() {
    Column {
        Text(
            "Welcome to Research Hub!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight(700)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Text(
            "Connect, Collaborate, and grow in academic research.",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight(400)
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
    }
}


@Preview(showBackground = true)
@Preview(device = TABLET, showBackground = true)
@Composable
private fun TestPreview() {
    ResearchHubTheme() {
        StudentHomeScreen()
    }
}