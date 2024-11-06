package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient

/**
 * Faculties use cases
 * This class is a use case to get all the faculties
 * @property client Research hub client
 * @constructor Create empty Faculties use cases
 * @see ResearchHubClient
 */
data class FacultiesUseCases(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke() = client.getAllFaculties()
}