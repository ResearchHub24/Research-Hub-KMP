package com.atech.research.ui.compose.teacher.home

import com.atech.research.core.ktor.model.ResearchModel

sealed interface HomeScreenEvents {
    data object RefreshData : HomeScreenEvents
    data class SetResearch(val model: ResearchModel?) : HomeScreenEvents
    data class OnEdit(val model: ResearchModel) : HomeScreenEvents
    data class SaveChanges(val onDone : () -> Unit) : HomeScreenEvents
}