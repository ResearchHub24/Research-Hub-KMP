package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.ForumModel
import com.atech.research.core.ktor.model.MessageModel

data class ForumUseCases(
    val getAllForum: GetAllForum,
    val createNewForum: CreateNewForum,
    val getAllMessage: GetAllMessage,
    val sendMessage: SendMessage
)


data class GetAllForum(
    private val api: ResearchHubClient
) {
    suspend operator fun invoke(uid: String, isAdmin: Boolean) = api.getAllForum(uid, isAdmin)
}

data class CreateNewForum(
    private val api: ResearchHubClient
) {
    suspend operator fun invoke(forumModel: ForumModel, message: MessageModel?) =
        api.createNewForum(forumModel, message)
}

data class GetAllMessage(
    private val api: ResearchHubClient
) {
    suspend operator fun invoke(path: String) = api.getAllMessage(path)
}

data class SendMessage(
    private val api: ResearchHubClient
) {
    suspend operator fun invoke(path: String, message: MessageModel) =
        api.sendMessage(path, message)
}