package com.atech.research.ui.compose.profile

import com.atech.research.core.ktor.model.EducationDetails
import com.atech.research.core.ktor.model.LinkModel

sealed interface ProfileEvents {
    data object LoadData : ProfileEvents
    data class OnEditClick(
        val model: EducationDetails? = null,
    ) : ProfileEvents

    data class OnEducationEdit(
        val model: EducationDetails,
    ) : ProfileEvents

    data class OnSaveEducationClick(
        val educationDetailsList: List<EducationDetails>,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents

    data class OnDeleteEducationClick(
        val educationDetailsList: List<EducationDetails>,
        val yetToDelete: EducationDetails,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents

    data class OnLinkClick(
        val link: LinkModel?,
    ) : ProfileEvents

    data class OnAddLinkClick(
        val linkList: List<LinkModel>,
        val link: LinkModel,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents

    data class OnDeleteLinkClick(
        val linkList: List<LinkModel>,
        val yetToDelete: LinkModel,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents
}