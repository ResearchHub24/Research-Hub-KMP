package com.atech.research.ui.compose.teacher.home

sealed interface HomeScreenEvents {
    data object RefreshData : HomeScreenEvents
}