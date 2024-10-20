package com.atech.research.core.ktor.model

import androidx.annotation.Keep

@Keep
data class ResearchApplyModel(
    val userName: String,
    val userUid: String,
    val userProfile: String,
    val researchId: String,
    val answers: List<AnswerModel>,
    val created: Long = System.currentTimeMillis()
)

@Keep
data class AnswerModel(
    val question: String,
    val answer: String,
)