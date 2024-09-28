package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient

data class UserUseCases(
    val getUserDetail: GetUseDetailUseCase
)

data class GetUseDetailUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(uid: String) = client.getUser(uid)
}