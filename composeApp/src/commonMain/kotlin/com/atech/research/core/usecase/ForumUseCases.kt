package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.ForumModel
import com.atech.research.core.ktor.model.MessageModel

/**
 * Forum use cases
 * This class is a wrapper for all the use cases related to the forum
 * @property getAllForum Get all forum use case
 * @property createNewForum Create new forum use case
 * @property getAllMessage Get all message use case
 * @property sendMessage Send message use case
 * @constructor Create empty Forum use cases
 * @see GetAllForum
 * @see CreateNewForum
 * @see GetAllMessage
 * @see SendMessage
 */
data class ForumUseCases(
    val getAllForum: GetAllForum,
    val createNewForum: CreateNewForum,
    val getAllMessage: GetAllMessage,
    val sendMessage: SendMessage
)

/**
 * Get all forum
 * This class is a use case to get all the forum
 * @property api Research hub client
 * @constructor Create empty Get all forum
 * @see ResearchHubClient
 * @see ForumUseCases
 */
data class GetAllForum(
    private val api: ResearchHubClient
) {
    suspend operator fun invoke(uid: String, isAdmin: Boolean) = api.getAllForum(uid, isAdmin)
}

/**
 * Create new forum
 * This class is a use case to create a new forum
 * @property api Research hub client
 * @constructor Create empty Create new forum
 * @see ResearchHubClient
 * @see ForumUseCases
 */
data class CreateNewForum(
    private val api: ResearchHubClient
) {
    suspend operator fun invoke(forumModel: ForumModel, message: MessageModel?) =
        api.createNewForum(forumModel, message)
}

/**
 * Get all message
 * This class is a use case to get all the message
 * @property api Research hub client
 * @constructor Create empty Get all message
 * @see ResearchHubClient
 * @see ForumUseCases
 */
data class GetAllMessage(
    private val api: ResearchHubClient
) {
    suspend operator fun invoke(path: String) = api.getAllMessage(path)
}

/**
 * Send message
 * This class is a use case to send a message
 * @property api Research hub client
 * @constructor Create empty Send message
 * @see ResearchHubClient
 * @see ForumUseCases
 */
data class SendMessage(
    private val api: ResearchHubClient
) {
    suspend operator fun invoke(path: String, message: MessageModel) =
        api.sendMessage(path, message)
}