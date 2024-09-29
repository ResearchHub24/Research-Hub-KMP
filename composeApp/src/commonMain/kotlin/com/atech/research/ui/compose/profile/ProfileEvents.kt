package com.atech.research.ui.compose.profile

import com.atech.research.core.ktor.model.EducationDetails

sealed interface ProfileEvents {
    data object LoadData : ProfileEvents
    data class OnEditClick(
        val model: EducationDetails? = null,
    ) : ProfileEvents

    data class OnEducationEdit(
        val model: EducationDetails,
    ) : ProfileEvents
}