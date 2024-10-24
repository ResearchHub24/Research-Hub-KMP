package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient

data class ApplicationUseCases(
    val getAllApplicationsUseCase: GetALlApplicationsUseCase
)


data class GetALlApplicationsUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(researchId: String) =
        client.getAllAppliedResearchApplication(researchId)
}