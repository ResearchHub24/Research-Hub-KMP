package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient


data class StudentResearchUseCases(
    val getAllResearch: GetAllResearch
)


data class GetAllResearch(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke() = client.getPostedResearch()
}