package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.UserUpdateQueryHelper

data class UserUseCases(
    val getUserDetail: GetUseDetailUseCase,
    val updateUserDetail: UpdateUserDetailUseCase,
    val getAllSkills: GetAllSkillsUseCase
)

data class GetUseDetailUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(uid: String) = client.getUser(uid)
}

data class UpdateUserDetailUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        uid: String, vararg varargs: UserUpdateQueryHelper<Any>
    ) = client.updateUserData(uid, *varargs)
}

data class GetAllSkillsUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke() = client.getAllSkills()
}