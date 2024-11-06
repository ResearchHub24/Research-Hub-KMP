package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.notification.NotificationModel

/**
 * Research use case
 * This class is a wrapper for all the use cases related to the research
 * @property getAllPosts Get all posted research use case
 * @property updateOrPostResearch Update or post research use case
 * @property deleteResearch Delete research use case
 * @property sendNotificationUseCase Send notification use case
 * @property getResearchById Get research by id use case
 * @constructor Create empty Research use case
 * @see GetPostedResearchUseCase
 * @see UpdateOrPostResearchUseCase
 * @see DeleteResearchUseCase
 * @see SendNotificationUseCase
 * @see GetResearchById
 */
data class ResearchUseCase(
    val getAllPosts: GetPostedResearchUseCase,
    val updateOrPostResearch: UpdateOrPostResearchUseCase,
    val deleteResearch: DeleteResearchUseCase,
    val sendNotificationUseCase: SendNotificationUseCase,
    val getResearchById: GetResearchById
)

/**
 * Get posted research use case
 * This class is a use case to get all the posted research
 * @property client Research hub client
 * @constructor Create empty Get posted research use case
 * @see ResearchHubClient
 * @see ResearchUseCase
 */
data class GetPostedResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        userId: String? = null
    ) =
        client.getPostedResearch(userId)
}

/**
 * Update or post research use case
 * This class is a use case to update or post the research
 * @property client Research hub client
 * @constructor Create empty Update or post research use case
 * @see ResearchHubClient
 * @see ResearchUseCase
 */
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

/**
 * Delete research use case
 * This class is a use case to delete the research
 * @property client Research hub client
 * @constructor Create empty Delete research use case
 * @see ResearchHubClient
 * @see ResearchUseCase
 */
data class DeleteResearchUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        id: String
    ) = client.deleteResearch(id)
}

/**
 * Send notification use case
 * This class is a use case to send the notification
 * @property client Research hub client
 * @constructor Create empty Send notification use case
 * @see ResearchHubClient
 * @see ResearchUseCase
 */
data class SendNotificationUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        topic: String,
        model: NotificationModel
    ) = client.sendNotificationToTopic(
        topic = topic,
        model = model
    )
}

/**
 * Get research by id
 * This class is a use case to get the research by id
 * @property client Research hub client
 * @constructor Create empty Get research by id
 * @see ResearchHubClient
 * @see ResearchUseCase
 */
data class GetResearchById(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        researchPath: String
    ) = client.getResearchById(researchPath)
}