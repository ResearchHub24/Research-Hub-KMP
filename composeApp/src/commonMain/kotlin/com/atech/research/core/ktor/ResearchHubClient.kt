package com.atech.research.core.ktor

import com.atech.research.core.model.SuccessResponse
import com.atech.research.core.model.UserModel
import com.atech.research.core.model.UserUpdateQueryHelper
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
    ): DataState<String>

    companion object {
        private const val BASE_URL = "http://192.168.29.205:9090/api/v1"

        //        private const val BASE_URL = "http://192.168.17.1:9090/api/v1"
        const val USER = "$BASE_URL/users"
        const val LOGIN = "$BASE_URL/login"
    }
}