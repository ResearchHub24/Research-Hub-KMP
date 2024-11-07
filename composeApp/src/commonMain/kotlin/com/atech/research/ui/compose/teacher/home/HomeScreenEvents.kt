package com.atech.research.ui.compose.teacher.home

import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.TagModel

/**
 * Home Screen Events
 */
sealed interface HomeScreenEvents {
    /**
     * Set user id
     *
     * @property userId String
     * @constructor Create empty Set user id
     */
    data class SetUserId(val userId: String) : HomeScreenEvents
    /**
     * Refresh data
     */
    data object RefreshData : HomeScreenEvents

    /**
     * Set research
     * @property model ResearchModel?
     * @constructor Create empty Set research
     * @see ResearchModel
     */
    data class SetResearch(val model: ResearchModel?) : HomeScreenEvents

    /**
     * On edit
     * @property model ResearchModel
     * @constructor Create empty On edit
     * @see ResearchModel
     */
    data class OnEdit(val model: ResearchModel) : HomeScreenEvents

    /**
     * Save changes
     * @property onDone Function0<Unit>
     * @constructor Create empty Save changes
     */
    data class SaveChanges(val onDone: () -> Unit) : HomeScreenEvents
    /**
     * Load tags
     */
    data object LoadTags : HomeScreenEvents

    /**
     * Add tag
     * @property tag TagModel
     * @property onDone Function1<Exception?, Unit>
     * @constructor Create empty Add tag
     * @see TagModel
     */
    data class AddTag(val tag: TagModel, val onDone: (Exception?) -> Unit) : HomeScreenEvents

    /**
     * Delete research
     * @property model ResearchModel
     * @property onDone Function1<Exception?, Unit>
     * @constructor Create empty Delete research
     * @see ResearchModel
     */
    data class DeleteResearch(val model: ResearchModel, val onDone: (Exception?) -> Unit) :
        HomeScreenEvents

    /**
     * Load student profile
     * @property userId String
     * @constructor Create empty Load student profile
     */
    data class LoadStudentProfile(val userId: String) : HomeScreenEvents

    /**
     * On title and image url change
     * @property title String
     * @property imageLink String?
     * @constructor Create empty On title and image url change
     */
    data class OnTitleAndImageUrlChange(val title: String, val imageLink: String? = null) :
        HomeScreenEvents

    /**
     * Send notification
     * @property title String
     * @property researchId String
     * @property imageLink String?
     * @property created Long
     * @property onDone Function1<String?, Unit>
     * @constructor Create empty Send notification
     */
    data class SendNotification(
        val title: String,
        val researchId: String,
        val imageLink: String? = null,
        val created: Long = System.currentTimeMillis(),
        val onDone: (String?) -> Unit = {}
    ) : HomeScreenEvents
}