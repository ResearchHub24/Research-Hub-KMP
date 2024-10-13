package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class ResearchModel(
    val title: String,
    val description: String,
    val author: String,
    val authorUid: String,
    val authorEmail: String,
    val authorPhoto: String,
    val tags: List<TagModel> = emptyList(),
    val questions: List<String> = emptyList(),
    val path: String,
    val requirements: Requirements = Requirements(),
    val created: Long = System.currentTimeMillis(),
    val deadline: Long? = null,
)

@Keep
@Serializable
data class Requirements(
    val req: String = "",
    val minCgpa: Double? = null,
)

@Keep
@Serializable
data class TagModel(
    val created: Long = System.currentTimeMillis(),
    val createdBy: String,
    val name: String,
)
