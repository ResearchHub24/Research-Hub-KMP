package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * Application model
 * This is a application model that is used to store the application data
 * @property userName The name of the user
 * @property userUid The user uid
 * @property userProfile The user profile
 * @property userEmail The user email
 * @property researchId The research id
 * @property researchTitle The research title
 * @property answers The list of answers
 * @property action The action
 * @property created The created time
 * @constructor Create empty Application model
 * @see AnswerModel
 * @see Action
 */
@Keep
@Serializable
data class ApplicationModel(
    val userName: String,
    val userUid: String,
    val userProfile: String,
    val userEmail: String,
    val researchId: String,
    var researchTitle: String,
    val answers: List<AnswerModel>,
    val action: Action = Action.PENDING,
    val created: Long = System.currentTimeMillis()
)

/**
 * Answer model
 * This is a answer model that is used to store the answer data
 * @property question The question
 * @property answer The answer
 * @constructor Create empty Answer model
 * @see ApplicationModel
 */
@Keep
@Serializable
data class AnswerModel(
    val question: String,
    val answer: String,
)

/**
 * Action
 * This is a action that is used to store the action data
 * @constructor Create empty Action
 * @see ApplicationModel
 */
@Keep
@Serializable
enum class Action {
    PENDING, SELECTED, REJECTED
}