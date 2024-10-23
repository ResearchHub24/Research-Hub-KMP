package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.ApplicationModel


data class StudentResearchUseCases(
    val getAllResearch: GetAllResearch,
    val applyResearchUseCase: ApplyResearchUseCase,
    val isAppliedToResearchUseCase: IsAppliedToResearchUseCase
)


data class GetAllResearch(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke() = client.getPostedResearch()
}

data class ApplyResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(researchId: String, researchModel: ApplicationModel) =
        client.postAppliedResearch(researchId, researchModel)
}


data class IsAppliedToResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(researchId: String, userId: String) =
        client.isAppliedToResearch(researchId, userId)
}


data class AllApplicationsUseCases(
    private val client: ResearchHubClient
){
    suspend operator fun invoke(userId: String) = client.getAppliedResearch(userId)
}