package com.atech.research.core.usecase

import com.atech.research.core.ktor.ResearchHubClient

data class FacultiesUseCases(
    private val client: ResearchHubClient
) {
    suspend operator fun invoke() = client.getAllFaculties()
}