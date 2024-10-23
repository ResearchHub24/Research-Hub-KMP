package com.atech.research.ui.compose.student.application

import com.atech.research.core.ktor.model.ApplicationModel

sealed interface ApplicationEvents {
    data object LoadApplication : ApplicationEvents
    data class ApplicationSelected(val applicationId: ApplicationModel) : ApplicationEvents
}