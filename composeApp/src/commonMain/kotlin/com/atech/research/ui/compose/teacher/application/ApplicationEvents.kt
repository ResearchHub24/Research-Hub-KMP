package com.atech.research.ui.compose.teacher.application

import com.atech.research.core.ktor.model.ApplicationModel

sealed interface ApplicationEvents {
    data class LoadData(val researchId: String) : ApplicationEvents
}