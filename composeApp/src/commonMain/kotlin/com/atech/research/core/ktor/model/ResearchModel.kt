package com.atech.research.core.ktor.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

/**
 * Research model
 * This is a research model that is used to store the research data
 * @property title The title of the research
 * @property description The description of the research
 * @property author The author of the research
 * @property authorUid The author uid of the research
 * @property authorEmail The author email of the research
 * @property authorPhoto The author photo of the research
 * @property tags The list of tags
 * @property questions The list of questions
 * @property path The path
 * @property requirements The requirements
 * @property created The created time
 * @property deadline The deadline
 * @constructor Create empty Research model
 * @see Requirements
 * @see TagModel
 */
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

/**
 * Requirements
 * This is a requirements that is used to store the requirements data for the research
 * @property req The requirements
 * @property minCgpa The minimum cgpa
 * @constructor Create empty Requirements
 * @see ResearchModel
 */
@Keep
@Serializable
data class Requirements(
    val req: String = "",
    val minCgpa: Double? = null,
)

/**
 * Tag model
 * This is a tag model that is used to store the tag data
 * @property created The created time
 * @property createdBy The created by
 * @property name The name of the tag
 * @constructor Create empty Tag model
 * @see ResearchModel
 */
@Keep
@Serializable
data class TagModel(
    val created: Long = System.currentTimeMillis(),
    val createdBy: String = "",
    val name: String,
)
