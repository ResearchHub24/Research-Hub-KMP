package com.atech.research.ui.compose.student.home

import com.atech.research.core.ktor.model.ResearchModel

/**
 * Student Home Events
 */
sealed interface StudentHomeEvents {
    /**
     * Load data
     *
     * @constructor Create empty Load data
     */
    data object LoadData : StudentHomeEvents

    /**
     * On research click
     *
     * @property model ResearchModel?
     * @constructor Create empty On research click
     * @see ResearchModel
     */
    data class OnResearchClick(val model: ResearchModel?) : StudentHomeEvents

    /**
     * Load user profile
     *
     * @property userId String
     * @constructor Create empty Load user profile
     */
    data class LoadUserProfile(val userId: String) : StudentHomeEvents

    /**
     * Set research from deep link
     *
     * @property researchPath String
     * @property onComplete Function1<Boolean, Unit>
     * @constructor Create empty Set research from deep link
     */
    data class SetResearchFromDeepLink(
        val researchPath: String,
        val onComplete: (Boolean) -> Unit
    ) :
        StudentHomeEvents
}