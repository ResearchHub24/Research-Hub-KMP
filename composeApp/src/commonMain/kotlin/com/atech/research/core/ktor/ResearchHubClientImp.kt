package com.atech.research.core.ktor

import com.atech.research.core.model.UserModel
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.researchHubLog
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ResearchHubClientImp(
    private val client: HttpClient
) : ResearchHubClient {
    override suspend fun getUser(uid: String): DataState<UserModel> =
        try {
            DataState.Success(client.get {
                url("${ResearchHubClient.USER}/$uid")
                contentType(ContentType.Application.Json)
            }.body<UserModel>())
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
}