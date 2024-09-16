package com.atech.research.core.ktor

import com.atech.research.core.model.UserModel
import com.atech.research.utils.DataState

interface ResearchHubClient {

    suspend fun getUser(uid: String): DataState<UserModel>

    companion object {
        private const val BASE_URL = "http://192.168.29.205:9090/api/v1"
        const val USER = "$BASE_URL/users"
    }
}