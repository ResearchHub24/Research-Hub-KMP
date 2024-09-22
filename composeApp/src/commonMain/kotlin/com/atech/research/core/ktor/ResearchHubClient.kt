package com.atech.research.core.ktor

import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.SuccessResponse
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.ktor.model.UserUpdateQueryHelper
import com.atech.research.utils.DataState


interface ResearchHubClient {

    suspend fun getUser(uid: String): DataState<UserModel>


    suspend fun updateUserData(
        uid: String,
        vararg varargs: UserUpdateQueryHelper<Any>
    ): DataState<SuccessResponse>

    suspend fun logInUser(
        email: String,
        password: String
    ): DataState<UserModel>


    suspend fun getPostedResearch(userId: String? = null): DataState<List<ResearchModel>>

    suspend fun updateResearch(researchModel: ResearchModel): DataState<SuccessResponse>

    suspend fun postResearch(researchModel: ResearchModel): DataState<SuccessResponse>

    suspend fun getAllTags(): DataState<List<TagModel>>

    suspend fun addTag(tagModel: TagModel): DataState<String>

    suspend fun deleteTag(tagModel: TagModel): DataState<String>


    companion object {
        private const val BASE_URL = "http://192.168.29.205:9090/api/v1"

        //        private const val BASE_URL = "http://192.168.17.1:9090/api/v1"
        const val USER = "$BASE_URL/users"
        const val LOGIN = "$BASE_URL/login"
        const val RESEARCH = "$BASE_URL/research"
        const val RESEARCH_POST = "$RESEARCH/post"
        const val TAGS = "$RESEARCH/tags"
    }
}