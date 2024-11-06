package com.atech.research.ui.compose.forum

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ChatMessage
import com.atech.research.core.ktor.model.ForumModel
import com.atech.research.core.ktor.model.UserType
import com.atech.research.core.ktor.model.toChatMessage
import com.atech.research.core.usecase.ForumUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchHubViewModel
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.researchHubLog
import kotlinx.coroutines.launch

class ForumViewModel(
    private val forumUseCases: ForumUseCases, private val prefManager: PrefManager
) : ResearchHubViewModel() {
    private val uid = prefManager.getString(Prefs.USER_ID.name)

    private val _allForum = mutableStateOf<DataState<List<ForumModel>>>(DataState.Loading)
    val allForum: State<DataState<List<ForumModel>>> get() = _allForum

    private val _allMessage = mutableStateOf<DataState<List<ChatMessage>>>(DataState.Loading)
    val allMessage: State<DataState<List<ChatMessage>>> get() = _allMessage

    fun onEvent(event: ForumEvents) {
        when (event) {
            ForumEvents.LoadChat -> loadData()
            is ForumEvents.OnChatClick -> loadMessage(event.path)
        }
    }

    private fun loadMessage(path: String) = scope.launch {
        _allMessage.value = DataState.Loading
        when (val mDataState = forumUseCases.getAllMessage.invoke(path)) {
            is DataState.Loading -> _allMessage.value = mDataState
            is DataState.Success -> _allMessage.value =
                DataState.Success(mDataState.data.toChatMessage(uid))

            is DataState.Error -> _allMessage.value = mDataState
        }
    }

    private fun loadData() = scope.launch {
        _allForum.value = forumUseCases.getAllForum.invoke(uid, isTeacher())
    }

    private fun isTeacher() = prefManager.getString(Prefs.USER_TYPE.name) == UserType.TEACHER.name
}