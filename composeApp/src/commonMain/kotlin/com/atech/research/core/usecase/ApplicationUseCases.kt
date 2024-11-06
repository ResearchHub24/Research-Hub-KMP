package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.Action

/**
 * Application use cases
 * This class is a wrapper for all the use cases related to the application
 * @property getAllApplicationsUseCase Get all applications use case
 * @property changeStatusUseCases Change status use cases
 * @constructor Create empty Application use cases
 * @see GetALlApplicationsUseCase
 * @see ChangeStatusUseCases
 */
data class ApplicationUseCases(
    val getAllApplicationsUseCase: GetALlApplicationsUseCase,
    val changeStatusUseCases: ChangeStatusUseCases
)

/**
 * Get all applications use case
 * This class is a use case to get all the applications
 * @property client Research hub client
 * @constructor Create empty Get a ll applications use case
 * @see ResearchHubClient
 * @see ApplicationUseCases
 */
data class GetALlApplicationsUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(researchId: String) =
        client.getAllAppliedResearchApplication(researchId)
}

/**
 * Change status use cases
 * This class is a use case to change the status of the application
 * @property client Research hub client
 * @constructor Create empty Change status use cases
 * @see ResearchHubClient
 * @see ApplicationUseCases
 */
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