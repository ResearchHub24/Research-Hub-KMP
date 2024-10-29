package com.atech.research.core.ktor

import com.atech.research.core.ktor.model.ApplicationModel
import com.atech.research.core.ktor.model.ErrorResponse
import com.atech.research.core.ktor.model.LoginResponse
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.SuccessResponse
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.ktor.model.UserUpdateQueryHelper
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.checkError
import com.atech.research.utils.getWithFallback
import com.atech.research.utils.researchHubLog
import com.atech.research.utils.toJson
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ResearchHubClientImp(
    private val client: HttpClient
) : ResearchHubClient {
    override suspend fun getUser(uid: String): DataState<UserModel> = try {
        checkError<UserModel, ErrorResponse>(client.getWithFallback {
            url("${ResearchHubClient.USER}/$uid")
            contentType(ContentType.Application.Json)
        })
    } catch (e: Exception) {
        researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
        DataState.Error(e)
    }

    override suspend fun updateUserData(
        uid: String, vararg varargs: UserUpdateQueryHelper<Any>
    ): DataState<SuccessResponse> = try {
        checkError<SuccessResponse, ErrorResponse>(
            client.post {
                val query = varargs.joinToString("&") {
                    when (it) {
                        is UserUpdateQueryHelper.UpdateUserEducationDetails ->
                            "${it.queryType}=${it.value.toJson()}"

                        is UserUpdateQueryHelper.UpdateUserFilledForm ->
                            "${it.queryType}=${it.value.toJson()}"

                        is UserUpdateQueryHelper.UpdateUserLinks ->
                            "${it.queryType}=${it.value.toJson()}"

                        is UserUpdateQueryHelper.UpdateUserSkillList ->
                            "${it.queryType}=${it.value.toJson()}"

                        else -> "${it.queryType}=${it.value}"
                    }
                }
                url("${ResearchHubClient.USER}/$uid/update?$query")
                contentType(ContentType.Application.Json)
            }
        )
    } catch (
        e: Exception
    ) {
        researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
        DataState.Error(e)
    }

    override suspend fun logInUser(email: String, password: String): DataState<LoginResponse> =
        try {
            checkError<LoginResponse, ErrorResponse>(
                client.post {
                    url("${ResearchHubClient.LOGIN}?email=$email&password=$password")
                }
            )
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
        checkError<List<ResearchModel>, ErrorResponse>(
            client.getWithFallback {
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
            checkError<SuccessResponse, ErrorResponse>(
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
            checkError<SuccessResponse, ErrorResponse>(
                client.post {
                    url(ResearchHubClient.RESEARCH_POST)
                    contentType(ContentType.Application.Json)
                    setBody(researchModel)
                }
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }

    override suspend fun deleteResearch(id: String): DataState<SuccessResponse> {
        return try {
            checkError<SuccessResponse, ErrorResponse>(
                client.delete {
                    url("${ResearchHubClient.DELETE_RESEARCH}?researchId=$id")
                    contentType(ContentType.Application.Json)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }

    override suspend fun getAllTags(): DataState<List<TagModel>> {
        return try {
            checkError<List<TagModel>, ErrorResponse>(
                client.getWithFallback {
                    url(ResearchHubClient.TAGS)
                    contentType(ContentType.Application.Json)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }

    override suspend fun addTag(tagModel: TagModel): DataState<String> =
        try {
            checkError<String, String>(
                client.post {
                    url(ResearchHubClient.TAGS)
                    contentType(ContentType.Application.Json)
                    setBody(tagModel)
                }
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }


    override suspend fun deleteTag(tagModel: TagModel): DataState<String> {
        return try {
            DataState.Success(
                client.put {
                    url("${ResearchHubClient.RESEARCH_POST}/tags/delete?id=${tagModel.name}")
                    contentType(ContentType.Application.Json)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }

    override suspend fun getAllSkills(): DataState<List<String>> {
        return try {
            checkError<List<String>, ErrorResponse>(
                client.getWithFallback {
                    url(ResearchHubClient.SKILLS)
                    contentType(ContentType.Application.Json)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }

    override suspend fun postAppliedResearch(
        researchId: String,
        researchModel: ApplicationModel
    ): DataState<SuccessResponse> {
        return try {
            checkError<SuccessResponse, ErrorResponse>(
                client.post {
                    url("${ResearchHubClient.RESEARCH}/$researchId/apply")
                    contentType(ContentType.Application.Json)
                    setBody(researchModel)
                }
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }

    override suspend fun isAppliedToResearch(
        researchId: String,
        userId: String
    ): DataState<Boolean> {
        return try {
            checkError<Boolean, ErrorResponse>(
                client.getWithFallback {
                    url("${ResearchHubClient.RESEARCH}/$researchId/applied?userId=$userId")
                    contentType(ContentType.Application.Json)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }

    override suspend fun getAllFaculties(): DataState<List<UserModel>> {
        return try {
            checkError<List<UserModel>, ErrorResponse>(
                client.getWithFallback {
                    url("${ResearchHubClient.USER}/faculty")
                    contentType(ContentType.Application.Json)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }

    override suspend fun getAppliedResearch(userId: String): DataState<List<ApplicationModel>> {
        return try {
            checkError<List<ApplicationModel>, ErrorResponse>(
                client.getWithFallback {
                    url("${ResearchHubClient.USER}/$userId/applications")
                    contentType(ContentType.Application.Json)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }

    override suspend fun getAllAppliedResearchApplication(researchId: String): DataState<List<ApplicationModel>> {
        return try {
            checkError<List<ApplicationModel>, ErrorResponse>(
                client.getWithFallback {
                    url("${ResearchHubClient.RESEARCH}/$researchId/applications")
                    contentType(ContentType.Application.Json)
                }.body()
            )
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
            DataState.Error(e)
        }
    }


}