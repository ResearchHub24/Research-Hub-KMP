package com.atech.research.ui.compose.teacher.application

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.Action
import com.atech.research.core.ktor.model.ApplicationModel
import com.atech.research.core.ktor.model.ForumModel
import com.atech.research.core.ktor.model.MessageModel
import com.atech.research.core.usecase.ApplicationUseCases
import com.atech.research.core.usecase.CreateNewForum
import com.atech.research.utils.DataState
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchHubViewModel
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.researchHubLog
import kotlinx.coroutines.launch


/**
 * Research Applications View Model
 *
 * @property useCases Application use cases
 * @property pref Pref manager
 * @property createNewForum Create new forum
 * @constructor Create empty Research Applications View Model
 * @see ApplicationUseCases
 * @see PrefManager
 * @see CreateNewForum
 */
class ResearchApplicationsViewModel(
    private val useCases: ApplicationUseCases,
    private val pref: PrefManager,
    private val createNewForum: CreateNewForum
) : ResearchHubViewModel() {

    private val _allApplication =
        mutableStateOf<DataState<List<ApplicationModel>>>(DataState.Loading)
    val allApplication: State<DataState<List<ApplicationModel>>> get() = _allApplication


    fun onEvent(
        event: ApplicationEvents
    ) {
        when (event) {
            is ApplicationEvents.LoadData -> loadData(researchId = event.researchId)
            is ApplicationEvents.OnStatusChange -> scope.launch {
                val dataState = useCases.changeStatusUseCases.invoke(
                    event.researchId,
                    event.application.userUid,
                    event.action
                )
                if (dataState is DataState.Error) {
                    event.onComplete(dataState.exception.message)
                    return@launch
                }
                val forumModel = ForumModel(
                    createdChatUid = pref.getString(Prefs.USER_ID.name),
                    createdChatUserEmail = pref.getString(Prefs.USER_EMAIL.name),
                    createdChatProfileUrl = pref.getString(Prefs.USER_PROFILE_URL.name),
                    createdChatUserName = pref.getString(Prefs.USER_NAME.name),

                    receiverChatUid = event.application.userUid,
                    receiverChatUserEmail = event.application.userEmail,
                    receiverChatUserName = event.application.userName,
                    receiverChatProfileUrl = event.application.userProfile,
                )

                val messageModel = MessageModel(
                    senderUid = pref.getString(Prefs.USER_ID.name),
                    senderName = pref.getString(Prefs.USER_NAME.name),
                    receiverName = event.application.userName,
                    receiverUid = event.application.userUid,
                    message = "Hello ${event.application.userName} 👋, thanks for applying! 🌟 Let’s discuss the project details and how you might get involved. 💼✨\n\nBest,\n${
                        pref.getString(
                            Prefs.USER_NAME.name
                        )
                    } 😊",
                )
                if (event.action == Action.SELECTED) {
                    val createFormDataState = createNewForum.invoke(forumModel, messageModel)
                    if (createFormDataState is DataState.Error) {
                        researchHubLog(
                            ResearchLogLevel.ERROR,
                            createFormDataState.exception.message ?: "Error creating forum"
                        )
                        event.onComplete(createFormDataState.exception.message)
                        return@launch
                    }
                }
                event.onComplete(null)
                loadData(researchId = event.researchId)
            }
        }
    }

    private fun loadData(researchId: String) = scope.launch {
        _allApplication.value = useCases.getAllApplicationsUseCase.invoke(researchId)
    }
}