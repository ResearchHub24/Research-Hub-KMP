package com.atech.research.core.ktor

import com.atech.research.core.ktor.model.Action
import com.atech.research.core.ktor.model.ApplicationModel
import com.atech.research.core.ktor.model.LoginResponse
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.SuccessResponse
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.ktor.model.UserUpdateQueryHelper
import com.atech.research.core.notification.NotificationModel
import com.atech.research.utils.DataState


interface ResearchHubClient {

    suspend fun getUser(uid: String): DataState<UserModel>


    suspend fun updateUserData(
        uid: String, vararg varargs: UserUpdateQueryHelper<Any>
    ): DataState<SuccessResponse>

    suspend fun logInUser(
        email: String, password: String
    ): DataState<LoginResponse>


    suspend fun getPostedResearch(userId: String? = null): DataState<List<ResearchModel>>

    suspend fun getResearchById(researchPath: String): DataState<ResearchModel>

    suspend fun updateResearch(researchModel: ResearchModel): DataState<SuccessResponse>

    suspend fun postResearch(researchModel: ResearchModel): DataState<SuccessResponse>

    suspend fun deleteResearch(id: String): DataState<SuccessResponse>

    suspend fun getAllTags(): DataState<List<TagModel>>

    suspend fun addTag(tagModel: TagModel): DataState<String>

    suspend fun deleteTag(tagModel: TagModel): DataState<String>

    suspend fun getAllSkills(): DataState<List<String>>

    suspend fun postAppliedResearch(
        researchId: String, researchModel: ApplicationModel
    ): DataState<SuccessResponse>

    suspend fun isAppliedToResearch(
        researchId: String, userId: String
    ): DataState<Boolean>

    suspend fun getAllFaculties(): DataState<List<UserModel>>

    suspend fun getAppliedResearch(userId: String): DataState<List<ApplicationModel>>

    suspend fun getAllAppliedResearchApplication(researchId: String): DataState<List<ApplicationModel>>

    suspend fun changeApplicationStatus(
        researchId: String, userUid: String, action: Action
    ): DataState<SuccessResponse>


    suspend fun sendNotificationToTopic(
        model: NotificationModel,
        topic: String
    ): DataState<SuccessResponse>


    companion object {
        //        private const val BASE_URL = "http://192.168.29.205:9090/api/v1"
        private const val BASE_URL = "http://192.168.153.65:9090/api/v1"

        const val USER = "$BASE_URL/users"
        const val LOGIN = "$BASE_URL/login"
        const val RESEARCH = "$BASE_URL/research"
        const val RESEARCH_POST = "$RESEARCH/post"
        const val DELETE_RESEARCH = "$RESEARCH/delete"
        const val TAGS = "$RESEARCH/tags"
        const val SKILLS = "$RESEARCH/skills"
        private const val SEND_NOTIFICATION = "$BASE_URL/notification"
        const val TOPIC = "$SEND_NOTIFICATION/topics"
    }
}