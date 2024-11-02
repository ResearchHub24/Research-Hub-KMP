package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

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

@Keep
@Serializable
data class AnswerModel(
    val question: String,
    val answer: String,
)


@Keep
@Serializable
enum class Action {
    PENDING, SELECTED, REJECTED
}