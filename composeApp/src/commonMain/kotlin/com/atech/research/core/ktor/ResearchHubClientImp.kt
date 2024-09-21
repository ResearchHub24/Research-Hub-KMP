package com.atech.research.core.ktor

import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.SuccessResponse
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.ktor.model.UserUpdateQueryHelper
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.researchHubLog
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

class ResearchHubClientImp(
    private val client: HttpClient
) : ResearchHubClient {
    override suspend fun getUser(uid: String): DataState<UserModel> = try {
        DataState.Success(client.get {
            url("${ResearchHubClient.USER}/$uid")
            contentType(ContentType.Application.Json)
        }.body<UserModel>())
    } catch (e: Exception) {
        researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
        DataState.Error(e)
    }

    override suspend fun updateUserData(
        uid: String, vararg varargs: UserUpdateQueryHelper<Any>
    ): DataState<SuccessResponse> = try {
        DataState.Success(client.post {
            val query = varargs.joinToString("&") {
                "${it.queryType}=${it.value}"
            }
            url("${ResearchHubClient.USER}/$uid/update?$query")
            contentType(ContentType.Application.Json)
        }.body<SuccessResponse>())
    } catch (
        e: Exception
    ) {
        researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
        DataState.Error(e)
    }

    override suspend fun logInUser(email: String, password: String): DataState<SuccessResponse> =
        try {

            val response = client.post {
                url("${ResearchHubClient.LOGIN}?email=$email&password=$password")
            }
            if (response.bodyAsText().contains("error")) {
                DataState.Error(Exception("Invalid email or password"))
            } else DataState.Success(response.body<SuccessResponse>())
        } catch (e: ResponseException) {
            when (e.response.status.value) {
                400 -> DataState.Error(Exception("Bad request"))
                else -> DataState.Error(e)
            }
        } catch (e: Exception) {
            println(e)
            DataState.Error(e)
        }

    override suspend fun getPostedResearch(userId: String?): DataState<List<ResearchModel>> = try {
        DataState.Success(
            client.get {
                if (userId != null) url("${ResearchHubClient.RESEARCH}?userId=$userId")
                else url(ResearchHubClient.RESEARCH)
                contentType(ContentType.Application.Json)
            }.body()
        )
    } catch (e: Exception) {
        researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
        DataState.Error(e)
    }

    override suspend fun updateResearch(researchModel: ResearchModel): DataState<SuccessResponse> =
        try {
            DataState.Success(
                client.put {
                    url(ResearchHubClient.RESEARCH_POST)
                    contentType(ContentType.Application.Json)
                    setBody(researchModel)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }


    override suspend fun postResearch(researchModel: ResearchModel): DataState<SuccessResponse> {
        return try {
            DataState.Success(
                client.post {
                    url("${ResearchHubClient.RESEARCH_POST}?model=${URLEncoder.encode(Json.encodeToString(researchModel), "UTF-8")}")
                    contentType(ContentType.Application.Json)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }


}