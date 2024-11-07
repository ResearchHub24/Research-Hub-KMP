package com.atech.research.ui.compose.student.application

import com.atech.research.core.ktor.model.ApplicationModel

/**
 * Application Events
 */
sealed interface ApplicationEvents {
    /**
     * Load application
     *
     * @constructor Create empty Load application
     */
    data object LoadApplication : ApplicationEvents

    /**
     * Application Selected
     *
     * @property applicationId Application model
     * @constructor Create empty Application Selected
     * @see ApplicationModel
     */
    data class ApplicationSelected(val applicationId: ApplicationModel) : ApplicationEvents
}