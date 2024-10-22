package com.atech.research.ui.compose.student.faculties

import com.atech.research.core.ktor.model.UserModel

sealed interface FacultiesEvent {
    data object LoadFaculties : FacultiesEvent
    data class FacultySelected(val facultyId: UserModel?) : FacultiesEvent
}