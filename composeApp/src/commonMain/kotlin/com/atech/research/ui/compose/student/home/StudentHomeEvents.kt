package com.atech.research.ui.compose.student.home

import com.atech.research.core.ktor.model.ResearchModel

sealed interface StudentHomeEvents {
    data object LoadData : StudentHomeEvents
    data class OnResearchClick(val model: ResearchModel?) : StudentHomeEvents
    data class LoadUserProfile(val userId: String) : StudentHomeEvents
}