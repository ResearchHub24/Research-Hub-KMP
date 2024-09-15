package com.atech.research.core.model

import androidx.annotation.Keep


enum class UserType {
    STUDENT, TEACHER
}

@Keep
data class UserModel(
    val uid: String?,
    val email: String?,
    val password: String? = null,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val created: Long = System.currentTimeMillis(),
    val phone: String? = null,
    val userType: String = UserType.STUDENT.name,
//    Student
    val educationDetails: String? = null,
    val skillList: String? = null,
    val filledForm: String? = null,
    val selectedForm: String? = null,
//    Teacher
    val verified: Boolean = false,
    val links: String? = null,
)
