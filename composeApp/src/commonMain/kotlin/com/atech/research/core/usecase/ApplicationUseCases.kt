package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.Action

data class ApplicationUseCases(
    val getAllApplicationsUseCase: GetALlApplicationsUseCase,
    val changeStatusUseCases: ChangeStatusUseCases
)


data class GetALlApplicationsUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(researchId: String) =
        client.getAllAppliedResearchApplication(researchId)
}

data class ChangeStatusUseCases(
    private val client : ResearchHubClient
){
    suspend operator fun invoke(
        researchId: String,
        userUid : String,
        action: Action
    ) = client.changeApplicationStatus(
        researchId = researchId,
        userUid = userUid,
        action=action
    )
}