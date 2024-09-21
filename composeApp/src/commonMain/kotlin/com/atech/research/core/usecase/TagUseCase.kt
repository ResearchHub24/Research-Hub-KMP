package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.TagModel

data class TagUseCase(
    val getAllTags: GetAllTagsUseCase,
    val addTag: AddTagUseCase,
    val deleteTag: DeleteTagUseCase
)


data class GetAllTagsUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke() = client.getAllTags()
}


data class AddTagUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        tag: TagModel
    ) = client.addTag(tag)
}

data class DeleteTagUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        tag: TagModel
    ) = client.deleteTag(tag)
}