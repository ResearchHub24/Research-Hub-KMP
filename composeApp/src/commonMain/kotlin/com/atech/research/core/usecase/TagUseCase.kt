package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.TagModel

/**
 * Tag use case
 * This class is a wrapper for all the use cases related to the tag
 * @property getAllTags Get all tags use case
 * @property addTag Add tag use case
 * @property deleteTag Delete tag use case
 * @constructor Create empty Tag use case
 * @see GetAllTagsUseCase
 * @see AddTagUseCase
 * @see DeleteTagUseCase
 */
data class TagUseCase(
    val getAllTags: GetAllTagsUseCase,
    val addTag: AddTagUseCase,
    val deleteTag: DeleteTagUseCase
)

/**
 * Get all tags use case
 * This class is a use case to get all the tags
 * @property client Research hub client
 * @constructor Create empty Get all tags use case
 * @see ResearchHubClient
 * @see TagUseCase
 */
data class GetAllTagsUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke() = client.getAllTags()
}

/**
 * Add tag use case
 * This class is a use case to add the tag
 * @property client Research hub client
 * @constructor Create empty Add tag use case
 * @see ResearchHubClient
 * @see TagUseCase
 */
data class AddTagUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        tag: TagModel
    ) = client.addTag(tag)
}

/**
 * Delete tag use case
 * This class is a use case to delete the tag
 * @property client Research hub client
 * @constructor Create empty Delete tag use case
 * @see ResearchHubClient
 * @see TagUseCase
 */
data class DeleteTagUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        tag: TagModel
    ) = client.deleteTag(tag)
}