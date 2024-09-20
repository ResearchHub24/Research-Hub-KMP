package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient


data class ResearchUseCase(
    val getAllPosts: GetPostedResearchUseCase
)


data class GetPostedResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        userId: String? = null
    ) =
        client.getPostedResearch(userId)
}