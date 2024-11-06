package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * User type
 * Enum class for user type
 * @constructor Create empty User type
 */
@Keep
@Serializable
enum class UserType {
    STUDENT, TEACHER
}

/**
 * User model
 * Base class to create new User model
 * @property uid String
 * @property email String
 * @property password String
 * @property displayName String
 * @property photoUrl String
 * @property created Long
 * @property phone String
 * @property userType [UserType]
 * @property educationDetails String
 * @property skillList List<String>
 * @property filledForm List<FilledForm>?
 * @property selectedForm List<String>
 * @property verified Boolean (only to verify faculties)
 * @property links List<LinkModel>
 * @constructor Create empty User model
 * @see UserType
 * @see FilledForm
 * @see LinkModel
 */
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

/**
 * Education details
 * Base class to create new Education details
 * @property university The name of the university
 * @property degree The degree
 * @property startYear The start year
 * @property endYear The end year
 * @property grade The grade
 * @property description The description
 * @property created The created time
 * @constructor Create empty Education details
 * @see UserModel
 */
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

/**
 * Link model
 * Base class to create new Link model
 * Only for teacher/faculty.
 * @property link The link
 * @property description The description
 * @property created The created time
 * @constructor Create empty Link model
 * @see UserModel
 */
@Keep
@Serializable
data class LinkModel(
    val link: String = "",
    val description: String = "",
    val created: Long = 0L,
)

/**
 * Filled form
 * Base class to create new Filled form
 * Only for student.
 * @property formId The form id
 * @property answers The list of answers
 * @constructor Create empty Filled form
 * @see Answer
 * @see UserModel
 */
@Keep
@Serializable
data class FilledForm(
    val formId: String,
    val answers: List<Answer>,
)

/**
 * Answer
 * Base class to create new Answer with question and answer
 * @property question The question
 * @property answer The answer
 * @constructor Create empty Answer
 * @see FilledForm
 */
@Keep
@Serializable
data class Answer(
    val question: String,
    val answer: String,
)

/**
 * User update query helper
 * Sealed class to create new User update query helper
 * @property value T
 * @property queryType String
 * @constructor Create empty User update query helper
 */
sealed class UserUpdateQueryHelper<out T : Any>(open val value: T, val queryType: String) {
    /**
     * Update user display name
     * @property value String
     * @constructor Create empty Update user display name
     */
    data class UpdateUserType(override val value: String) :
        UserUpdateQueryHelper<String>(value, "userType")

    /**
     * Update user password
     * @property value String
     * @constructor Create empty Update user password
     */
    data class UpdateUserPassword(override val value: String) :
        UserUpdateQueryHelper<String>(value, "password")

    /**
     * Update user phone
     *
     * @property value String
     * @constructor Create empty Update user phone
     */
    data class UpdateUserPhone(override val value: String) :
        UserUpdateQueryHelper<String>(value, "phone")

    /**
     * Update user education details
     *
     * @property value List<EducationDetails>
     * @constructor Create empty Update user education details
     * @see EducationDetails
     */
    data class UpdateUserEducationDetails(override val value: List<EducationDetails>) :
        UserUpdateQueryHelper<List<EducationDetails>>(value, "educationDetails")

    /**
     * Update user skill list
     *
     * @property value List<String>
     * @constructor Create empty Update user skill list
     */
    data class UpdateUserSkillList(override val value: List<String>) :
        UserUpdateQueryHelper<List<String>>(value, "skillList")

    /**
     * Update user filled form
     *
     * @property value List<FilledForm>
     * @constructor Create empty Update user filled form
     * @see FilledForm
     */
    data class UpdateUserFilledForm(override val value: List<FilledForm>) :
        UserUpdateQueryHelper<List<FilledForm>>(value, "filledForm")

    /**
     * Update user selected form
     *
     * @property value List<String>
     * @constructor Create empty Update user selected form
     */
    data class UpdateUserSelectedForm(override val value: List<String>) :
        UserUpdateQueryHelper<List<String>>(value, "selectedForm")

    /**
     * Update user verified
     *
     * @property value Boolean
     * @constructor Create empty Update user verified
     */
    data class UpdateUserVerified(override val value: Boolean) :
        UserUpdateQueryHelper<Boolean>(value, "verified")

    /**
     * Update user links
     *
     * @property value List<LinkModel>
     * @constructor Create empty Update user links
     * @see LinkModel
     */
    data class UpdateUserLinks(override val value: List<LinkModel>) :
        UserUpdateQueryHelper<List<LinkModel>>(value, "links")
}