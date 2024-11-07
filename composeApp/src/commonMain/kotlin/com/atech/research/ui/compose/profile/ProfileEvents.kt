package com.atech.research.ui.compose.profile

import com.atech.research.core.ktor.model.AnswerModel
import com.atech.research.core.ktor.model.EducationDetails
import com.atech.research.core.ktor.model.LinkModel

/**
 * Profile events
 * This is the profile events.
 */
sealed interface ProfileEvents {
    /**
     * Load data
     */
    data object LoadData : ProfileEvents
    /**
     * On edit click
     * @property model EducationDetails?
     * @see EducationDetails
     */
    data class OnEditClick(
        val model: EducationDetails? = null,
    ) : ProfileEvents

    /**
     * On education edit
     * @property model EducationDetails
     * @see EducationDetails
     */
    data class OnEducationEdit(
        val model: EducationDetails,
    ) : ProfileEvents

    /**
     * On save education click
     * @property educationDetailsList List<EducationDetails>
     * @property onComplete Function1<String?, Unit>
     * @see EducationDetails
     */
    data class OnSaveEducationClick(
        val educationDetailsList: List<EducationDetails>,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents

    /**
     * On delete education click
     * @property educationDetailsList List<EducationDetails>
     * @property yetToDelete EducationDetails
     * @property onComplete Function1<String?, Unit>
     * @see EducationDetails
     */
    data class OnDeleteEducationClick(
        val educationDetailsList: List<EducationDetails>,
        val yetToDelete: EducationDetails,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents

    /**
     * On link click
     * @property link LinkModel?
     * @see LinkModel
     */
    data class OnLinkClick(
        val link: LinkModel?,
    ) : ProfileEvents

    /**
     * On save link click
     * @property linkList List<LinkModel>
     * @property link LinkModel
     * @property onComplete Function1<String?, Unit>
     * @see LinkModel
     */
    data class OnAddLinkClick(
        val linkList: List<LinkModel>,
        val link: LinkModel,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents

    /**
     * On delete link click
     * @property linkList List<LinkModel>
     * @property yetToDelete LinkModel
     * @property onComplete Function1<String?, Unit>
     * @see LinkModel
     */
    data class OnDeleteLinkClick(
        val linkList: List<LinkModel>,
        val yetToDelete: LinkModel,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents

    /**
     * Perform sign out
     * @property onComplete Function1<String?, Unit>
     */
    data class PerformSignOut(
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents

    /**
     * Load skill list
     */
    data object LoadSkillList : ProfileEvents

    /**
     * On add skill click
     * @property skillList List<String>
     * @property onComplete Function1<String?, Unit>
     */
    data class OnAddSkillClick(
        val skillList: List<String>,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents

    /**
     * Apply research
     *
     * @property researchId The research id
     * @property researchTitle The research title
     * @property answerModelList The answer model list
     * @property onComplete The on complete
     * @constructor Create empty Apply research
     * @see AnswerModel
     */
    data class ApplyResearch(
        val researchId: String,
        val researchTitle: String,
        val answerModelList: List<AnswerModel>,
        val onComplete: (String?) -> Unit = {},
    ) : ProfileEvents
}