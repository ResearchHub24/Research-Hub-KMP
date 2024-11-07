package com.atech.research.ui.compose.teacher.application

import com.atech.research.core.ktor.model.Action
import com.atech.research.core.ktor.model.ApplicationModel

/**
 * Application Events
 */
sealed interface ApplicationEvents {
    /**
     * Load data
     *
     * @property researchId String
     * @constructor Create empty Load data
     */
    data class LoadData(val researchId: String) : ApplicationEvents

    /**
     * On status change
     *
     * @property researchId String
     * @property application Application model
     * @property action Action
     * @property onComplete Function1<String?, Unit>
     * @constructor Create empty On status change
     * @see ApplicationModel
     * @see Action
     */
    data class OnStatusChange(
        val researchId: String,
        val application: ApplicationModel,
        val action: Action,
        val onComplete: (String?) -> Unit = {}
    ) : ApplicationEvents
}