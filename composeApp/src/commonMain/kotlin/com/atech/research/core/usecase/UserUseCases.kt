package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.ktor.model.UserUpdateQueryHelper

/**
 * User use cases
 * This class is a wrapper for all the use cases related to the user
 * @property getUserDetail Get user detail use case
 * @property updateUserDetail Update user detail use case
 * @property getAllSkills Get all skills use case
 * @constructor Create empty User use cases
 * @see GetUseDetailUseCase
 * @see UpdateUserDetailUseCase
 * @see GetAllSkillsUseCase
 */
data class UserUseCases(
    val getUserDetail: GetUseDetailUseCase,
    val updateUserDetail: UpdateUserDetailUseCase,
    val getAllSkills: GetAllSkillsUseCase
)

/**
 * Get use detail use case
 * This class is a use case to get the user detail
 * @property client Research hub client
 * @constructor Create empty Get use detail use case
 * @see ResearchHubClient
 * @see UserUseCases
 */
data class GetUseDetailUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(uid: String) = client.getUser(uid)
}

/**
 * Update user detail use case
 * This class is a use case to update the user detail
 * @property client Research hub client
 * @constructor Create empty Update user detail use case
 * @see ResearchHubClient
 * @see UserUseCases
 */
data class UpdateUserDetailUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke(
        uid: String, vararg varargs: UserUpdateQueryHelper<Any>
    ) = client.updateUserData(uid, *varargs)
}

/**
 * Get all skills use case
 * This class is a use case to get all the skills
 * @property client Research hub client
 * @constructor Create empty Get all skills use case
 * @see ResearchHubClient
 * @see UserUseCases
 */
data class GetAllSkillsUseCase(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke() = client.getAllSkills()
}