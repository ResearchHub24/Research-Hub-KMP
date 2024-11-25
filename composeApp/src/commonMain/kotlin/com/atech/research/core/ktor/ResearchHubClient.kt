package com.atech.research.core.ktor

import com.atech.research.core.ktor.model.Action
import com.atech.research.core.ktor.model.ApplicationModel
import com.atech.research.core.ktor.model.ForumModel
import com.atech.research.core.ktor.model.LoginResponse
import com.atech.research.core.ktor.model.MessageModel
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.SuccessResponse
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.ktor.model.UserUpdateQueryHelper
import com.atech.research.core.notification.NotificationModel
import com.atech.research.utils.DataState

/**
 * Research hub client
 * This is an interface for Ktor client to interact with the Research Hub API.
 * @constructor Create empty Research hub client
 */
interface ResearchHubClient {

    /**
     * Get user
     * This function will get the user data from the API
     * @param uid
     * @return DataState<UserModel>
     * @see DataState
     * @see UserModel
     */
    suspend fun getUser(uid: String): DataState<UserModel>


    /**
     * Update user data
     * This function will update the user data in the API
     * @param uid User id
     * @param varargs UserUpdateQueryHelper
     * @return DataState<SuccessResponse>
     * @see UserUpdateQueryHelper
     * @see SuccessResponse
     * @see DataState
     */
    suspend fun updateUserData(
        uid: String, vararg varargs: UserUpdateQueryHelper<Any>
    ): DataState<SuccessResponse>

    /**
     * Log in user
     * This function will log in the user
     * @param email User email
     * @param password User password
     * @return DataState<LoginResponse>
     * @see LoginResponse
     * @see DataState
     *
     */
    suspend fun logInUser(
        email: String, password: String
    ): DataState<LoginResponse>


    /**
     * Is user verified
     * This function will check if the user is verified
     * @param uid User id
     * @return DataState<Boolean>
     * @see DataState
     */
    suspend fun isUserVerified(
        uid: String
    ): DataState<Boolean>


    /**
     * Get posted research
     * This function will get the posted research
     * @param userId User id
     * @return DataState<List<ResearchModel>>
     * @see ResearchModel
     * @see DataState
     */
    suspend fun getPostedResearch(userId: String? = null): DataState<List<ResearchModel>>

    /**
     * Get research by id
     * This function will get the research by id
     * @param researchPath Research id
     * @return DataState<ResearchModel>
     * @see ResearchModel
     * @see DataState
     */
    suspend fun getResearchById(researchPath: String): DataState<ResearchModel>

    /**
     * Update research
     * This function will update the research
     * @param researchModel Research model
     * @return DataState<SuccessResponse>
     * @see ResearchModel
     * @see SuccessResponse
     * @see DataState
     */
    suspend fun updateResearch(researchModel: ResearchModel): DataState<SuccessResponse>

    /**
     * Post research
     * This function will post the research
     * @param researchModel Research model
     * @return DataState<SuccessResponse>
     * @see ResearchModel
     * @see SuccessResponse
     * @see DataState
     */
    suspend fun postResearch(researchModel: ResearchModel): DataState<SuccessResponse>

    /**
     * Delete research
     * This function will delete the research
     * @param id Research id
     * @return DataState<SuccessResponse>
     * @see SuccessResponse
     * @see DataState
     */
    suspend fun deleteResearch(id: String): DataState<SuccessResponse>

    /**
     * Get all tags
     * This function will get all the tags
     * @return DataState<List<TagModel>>
     * @see TagModel
     * @see DataState
     */
    suspend fun getAllTags(): DataState<List<TagModel>>

    /**
     * Add tag
     * This function will add the tag
     * @param tagModel Tag model
     * @return DataState<String>
     * @see TagModel
     * @see DataState
     */
    suspend fun addTag(tagModel: TagModel): DataState<String>

    /**
     * Delete tag
     * This function will delete the tag
     * @param tagModel Tag model
     * @return DataState<String>
     * @see TagModel
     * @see DataState
     */
    suspend fun deleteTag(tagModel: TagModel): DataState<String>

    /**
     * Get all skills
     * This function will get all the skills
     * @return DataState<List<String>>
     * @see DataState
     */
    suspend fun getAllSkills(): DataState<List<String>>

    /**
     * Post applied research
     * This function will post the applied research
     * @param researchId Research id
     * @param researchModel Research model
     * @return DataState<SuccessResponse>
     * @see ApplicationModel
     * @see SuccessResponse
     * @see DataState
     */
    suspend fun postAppliedResearch(
        researchId: String, researchModel: ApplicationModel
    ): DataState<SuccessResponse>

    /**
     * Is applied to research
     * This function will check if the user is applied to the research
     * @param researchId Research id
     * @param userId User id
     * @return DataState<Boolean>
     * @see DataState
     */
    suspend fun isAppliedToResearch(
        researchId: String, userId: String
    ): DataState<Boolean>

    /**
     * Get all faculties
     * This function will get all the faculties
     * @return DataState<List<UserModel>>
     * @see UserModel
     * @see DataState
     */
    suspend fun getAllFaculties(): DataState<List<UserModel>>

    /**
     * Get all students
     * This function will get all the students
     * @return DataState<List<UserModel>>
     * @see UserModel
     * @see DataState
     */
    suspend fun getAppliedResearch(userId: String): DataState<List<ApplicationModel>>

    /**
     * Get all applied research application
     * This function will get all the applied research application
     * @param researchId Research id
     * @return DataState<List<ApplicationModel>>
     * @see ApplicationModel
     * @see DataState
     */
    suspend fun getAllAppliedResearchApplication(researchId: String): DataState<List<ApplicationModel>>

    /**
     * Change application status
     * This function will change the application status
     * @param researchId Research id
     * @param userUid User id
     * @param action Action
     * @return DataState<SuccessResponse>
     * @see Action
     * @see SuccessResponse
     * @see DataState
     */
    suspend fun changeApplicationStatus(
        researchId: String, userUid: String, action: Action
    ): DataState<SuccessResponse>

    /**
     * Send notification to topic
     * This function will send the notification to the topic
     * @param model Notification model
     * @param topic Topic
     * @return DataState<SuccessResponse>
     * @see NotificationModel
     * @see SuccessResponse
     * @see DataState
     */
    suspend fun sendNotificationToTopic(
        model: NotificationModel,
        topic: String
    ): DataState<SuccessResponse>

    /**
     * Get all forum
     * This function will get all the forum
     * @param uid User id
     * @param isAdmin Is admin
     * @return DataState<List<ForumModel>>
     * @see ForumModel
     * @see DataState
     */
    suspend fun getAllForum(
        uid: String,
        isAdmin: Boolean = false
    ): DataState<List<ForumModel>>

    /**
     * Create new forum
     * This function will create a new forum
     * @param forumModel Forum model
     * @param message Message model
     * @return DataState<SuccessResponse>
     * @see ForumModel
     * @see MessageModel
     * @see SuccessResponse
     * @see DataState
     */
    suspend fun createNewForum(
        forumModel: ForumModel,
        message: MessageModel? = null
    ): DataState<SuccessResponse>

    /**
     * Get all message
     * This function will get all the message
     * @param path Path
     * @return DataState<List<MessageModel>>
     * @see MessageModel
     * @see DataState
     */
    suspend fun getAllMessage(
        path: String
    ): DataState<List<MessageModel>>

    /**
     * Send message
     * This function will send the message
     * @param path Path
     * @param message Message model
     * @return DataState<SuccessResponse>
     * @see MessageModel
     * @see SuccessResponse
     * @see DataState
     */
    suspend fun sendMessage(
        path: String,
        message: MessageModel
    ): DataState<SuccessResponse>

    companion object {
        private const val BASE_URL = "http://192.168.29.205:9090/api/v1"
//        private const val BASE_URL = "http://192.168.153.65:9090/api/v1"

        const val USER = "$BASE_URL/users"
        const val LOGIN = "$BASE_URL/login"
        const val RESEARCH = "$BASE_URL/research"
        const val RESEARCH_POST = "$RESEARCH/post"
        const val DELETE_RESEARCH = "$RESEARCH/delete"
        const val TAGS = "$RESEARCH/tags"
        const val SKILLS = "$RESEARCH/skills"
        private const val SEND_NOTIFICATION = "$BASE_URL/notification"
        const val TOPIC = "$SEND_NOTIFICATION/topics"
        const val FORUM = "$BASE_URL/forum"
        const val MESSAGES = "$FORUM/messages"
    }
}