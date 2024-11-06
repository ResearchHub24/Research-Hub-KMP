package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.ApplicationModel


/**
 * Student research use cases
 * This class is a wrapper for all the use cases related to the student research
 * @property getAllResearch Get all research use case
 * @property applyResearchUseCase Apply research use case
 * @property isAppliedToResearchUseCase Is applied to research use case
 * @constructor Create empty Student research use cases
 * @see GetAllResearch
 * @see ApplyResearchUseCase
 * @see IsAppliedToResearchUseCase
 */
data class StudentResearchUseCases(
    val getAllResearch: GetAllResearch,
    val applyResearchUseCase: ApplyResearchUseCase,
    val isAppliedToResearchUseCase: IsAppliedToResearchUseCase
)


/**
 * Get all research use case
 * This class is a use case to get all the research
 * @property client Research hub client
 * @constructor Create empty Get all research
 * @see ResearchHubClient
 * @see StudentResearchUseCases
 */
data class GetAllResearch(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke() = client.getPostedResearch()
}

/**
 * Apply research use case
 * This class is a use case to apply the research
 * @property client Research hub client
 * @constructor Create empty Apply research use case
 * @see ResearchHubClient
 * @see StudentResearchUseCases
 */
data class ApplyResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(researchId: String, researchModel: ApplicationModel) =
        client.postAppliedResearch(researchId, researchModel)
}

/**
 * Is applied to research use case
 * This class is a use case to check if the user is applied to the research
 * @property client Research hub client
 * @constructor Create empty Is applied to research use case
 * @see ResearchHubClient
 * @see StudentResearchUseCases
 */
data class IsAppliedToResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(researchId: String, userId: String) =
        client.isAppliedToResearch(researchId, userId)
}

/**
 * All applications use cases
 * This class is a wrapper for all the use cases related to the all applications
 * @property client Research hub client
 * @constructor Create empty All applications use cases
 * @see ResearchHubClient
 */
data class AllApplicationsUseCases(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(userId: String) = client.getAppliedResearch(userId)
}