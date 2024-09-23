package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.ResearchModel


data class ResearchUseCase(
    val getAllPosts: GetPostedResearchUseCase,
    val updateOrPostResearch: UpdateOrPostResearchUseCase,
    val deleteResearch: DeleteResearchUseCase
)


data class GetPostedResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        userId: String? = null
    ) =
        client.getPostedResearch(userId)
}


data class UpdateOrPostResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        researchModel: ResearchModel
    ) = if (researchModel.path.isEmpty())
        client.postResearch(researchModel)
    else
        client.updateResearch(researchModel)

}

data class DeleteResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        id: String
    ) = client.deleteResearch(id)
}