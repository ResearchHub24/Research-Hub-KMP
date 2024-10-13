package com.atech.research.ui.compose.student.home

sealed interface StudentHomeEvents {
    data object LoadData : StudentHomeEvents
}