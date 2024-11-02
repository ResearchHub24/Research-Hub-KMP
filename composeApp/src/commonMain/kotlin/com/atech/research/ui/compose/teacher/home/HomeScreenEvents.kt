package com.atech.research.ui.compose.teacher.home

import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.TagModel

sealed interface HomeScreenEvents {
    data class SetUserId(val userId: String) : HomeScreenEvents
    data object RefreshData : HomeScreenEvents
    data class SetResearch(val model: ResearchModel?) : HomeScreenEvents
    data class OnEdit(val model: ResearchModel) : HomeScreenEvents
    data class SaveChanges(val onDone: () -> Unit) : HomeScreenEvents
    data object LoadTags : HomeScreenEvents
    data class AddTag(val tag: TagModel, val onDone: (Exception?) -> Unit) : HomeScreenEvents
    data class DeleteResearch(val model: ResearchModel, val onDone: (Exception?) -> Unit) :
        HomeScreenEvents

    data class LoadStudentProfile(val userId: String) : HomeScreenEvents

    data class SendNotification(
        val title: String,
        val researchId: String,
        val imageLink: String? = null,
        val onDone: (String?) -> Unit = {}
    ) : HomeScreenEvents
}