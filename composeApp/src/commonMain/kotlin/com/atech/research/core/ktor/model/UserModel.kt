package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
enum class UserType {
    STUDENT, TEACHER
}

@Keep
@Serializable
data class UserModel(
    val uid: String?,
    val email: String?,
    val password: String? = null,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val created: Long = System.currentTimeMillis(),
    val phone: String? = null,
    val userType: String? = UserType.STUDENT.name,
//    Student
    val educationDetails: String? = null,
    val skillList: String? = null,
    val filledForm: String? = null,
    val selectedForm: String? = null,
//    Teacher
    val verified: Boolean = false,
    val links: String? = null,
)

sealed class UserUpdateQueryHelper<out T : Any>(open val value: T, val queryType: String) {
    data class UpdateUserType(override val value: String) :
        UserUpdateQueryHelper<String>(value, "userType")

    data class UpdateUserPassword(override val value: String) :
        UserUpdateQueryHelper<String>(value, "password")

    data class UpdateUserPhone(override val value: String) :
        UserUpdateQueryHelper<String>(value, "phone")

    data class UpdateUserEducationDetails(override val value: String) :
        UserUpdateQueryHelper<String>(value, "educationDetails")

    data class UpdateUserSkillList(override val value: String) :
        UserUpdateQueryHelper<String>(value, "skillList")

    data class UpdateUserFilledForm(override val value: String) :
        UserUpdateQueryHelper<String>(value, "filledForm")

    data class UpdateUserSelectedForm(override val value: String) :
        UserUpdateQueryHelper<String>(value, "selectedForm")

    data class UpdateUserVerified(override val value: Boolean) :
        UserUpdateQueryHelper<Boolean>(value, "verified")

    data class UpdateUserLinks(override val value: String) :
        UserUpdateQueryHelper<String>(value, "links")
}
