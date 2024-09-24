package com.atech.research.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.atech.research.ui.theme.ResearchHubTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    forTeacher: Boolean = true
) {
    val appBarBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    MainContainer(
        title = "Profile",
        scrollBehavior = appBarBehavior,
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(appBarBehavior.nestedScrollConnection)
                .padding(paddingValue)
        ) {

        }
    }
}


@Composable
fun TopLayout(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    profileImage: String
) {

}

@Preview(
    showBackground = true,
    /*uiMode = UI_MODE_NIGHT_YES*/
)
@Composable
private fun TestPreview() {
    ResearchHubTheme() {

    }
}