package com.atech.research.ui.compose.student.faculties

import com.atech.research.core.ktor.model.UserModel

/**
 * Faculties Events
 */
sealed interface FacultiesEvent {
    /**
     * Load faculties
     *
     * @constructor Create empty Load faculties
     */
    data object LoadFaculties : FacultiesEvent

    /**
     * Faculty selected
     *
     * @property facultyId User model
     * @constructor Create empty Faculty selected
     * @see UserModel
     */
    data class FacultySelected(val facultyId: UserModel?) : FacultiesEvent
}