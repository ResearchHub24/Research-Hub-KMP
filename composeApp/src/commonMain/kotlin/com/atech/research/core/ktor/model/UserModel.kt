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
    val educationDetails: List<EducationDetails>? = null,
    val skillList: List<String>? = null,
    val filledForm: List<FilledForm>? = null,
    val selectedForm: List<String>? = null,
//    Teacher
    val verified: Boolean = false,
    val links: List<LinkModel>? = null,
)

@Keep
@Serializable
data class EducationDetails(
    val university: String = "",
    val degree: String = "",
    val startYear: String = "",
    val endYear: String? = null,
    val grade: String? = null,
    val description: String = "",
    val created: Long? = null
)

@Keep
@Serializable
data class LinkModel(
    val link: String = "",
    val description: String = "",
    val created: Long = 0L,
)

@Keep
@Serializable
data class FilledForm(
    val formId: String,
    val answers: List<Answer>,
)

@Keep
@Serializable
data class Answer(
    val question: String,
    val answer: String,
)

sealed class UserUpdateQueryHelper<out T : Any>(open val value: T, val queryType: String) {
    data class UpdateUserType(override val value: String) :
        UserUpdateQueryHelper<String>(value, "userType")

    data class UpdateUserPassword(override val value: String) :
        UserUpdateQueryHelper<String>(value, "password")

    data class UpdateUserPhone(override val value: String) :
        UserUpdateQueryHelper<String>(value, "phone")

    data class UpdateUserEducationDetails(override val value: List<EducationDetails>) :
        UserUpdateQueryHelper<List<EducationDetails>>(value, "educationDetails")

    data class UpdateUserSkillList(override val value: List<String>) :
        UserUpdateQueryHelper<List<String>>(value, "skillList")

    data class UpdateUserFilledForm(override val value: List<FilledForm>) :
        UserUpdateQueryHelper<List<FilledForm>>(value, "filledForm")

    data class UpdateUserSelectedForm(override val value: List<String>) :
        UserUpdateQueryHelper<List<String>>(value, "selectedForm")

    data class UpdateUserVerified(override val value: Boolean) :
        UserUpdateQueryHelper<Boolean>(value, "verified")

    data class UpdateUserLinks(override val value: List<LinkModel>) :
        UserUpdateQueryHelper<List<LinkModel>>(value, "links")
}