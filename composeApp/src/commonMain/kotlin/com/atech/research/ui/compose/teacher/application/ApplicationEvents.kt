package com.atech.research.ui.compose.teacher.application

import com.atech.research.core.ktor.model.Action

sealed interface ApplicationEvents {
    data class LoadData(val researchId: String) : ApplicationEvents
    data class OnStatusChange(
        val researchId: String,
        val userUid: String,
        val action: Action,
        val onComplete: (String?) -> Unit = {}
    ) : ApplicationEvents
}