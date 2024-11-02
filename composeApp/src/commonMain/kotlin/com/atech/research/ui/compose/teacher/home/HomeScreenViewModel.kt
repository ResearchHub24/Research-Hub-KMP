package com.atech.research.ui.compose.teacher.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.notification.Data
import com.atech.research.core.notification.Message
import com.atech.research.core.notification.Notification
import com.atech.research.core.notification.NotificationModel
import com.atech.research.core.usecase.GetUseDetailUseCase
import com.atech.research.core.usecase.ResearchUseCase
import com.atech.research.core.usecase.TagUseCase
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchHubViewModel
import com.atech.research.utils.Topics
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val researchUseCase: ResearchUseCase,
    private val getUseDetailUseCase: GetUseDetailUseCase,
    private val tagUseCase: TagUseCase,
) : ResearchHubViewModel() {

    private val _userId = mutableStateOf("")
    private val _researchModel = mutableStateOf<DataState<List<ResearchModel>>>(DataState.Loading)
    val allResearch: State<DataState<List<ResearchModel>>> get() = _researchModel

    private val _currentResearchModel = mutableStateOf<ResearchModel?>(null)
    val currentResearchModel: State<ResearchModel?> get() = _currentResearchModel

    private val _allTags = mutableStateOf<DataState<List<TagModel>>>(DataState.Loading)
    val allTags: State<DataState<List<TagModel>>> get() = _allTags


    private val _userProfile = mutableStateOf<DataState<UserModel>>(DataState.Loading)
    val userProfile: State<DataState<UserModel>> get() = _userProfile

    private val _titleAndUrl = mutableStateOf<Pair<String, String?>>(Pair("", null))
    val titleAndUrl: State<Pair<String, String?>> get() = _titleAndUrl

    init {
        onEvent(HomeScreenEvents.LoadTags)
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.SetUserId -> {
                _userId.value = event.userId
                onEvent(HomeScreenEvents.RefreshData)
            }

            is HomeScreenEvents.RefreshData -> loadData()
            is HomeScreenEvents.SetResearch -> _currentResearchModel.value = event.model
            is HomeScreenEvents.OnEdit -> _currentResearchModel.value = event.model
            is HomeScreenEvents.SaveChanges -> _currentResearchModel.value?.let {
                scope.launch {
                    researchUseCase.updateOrPostResearch.invoke(it)
                    loadData()
                    event.onDone()
                }
            }


            HomeScreenEvents.LoadTags -> loadTags()

            is HomeScreenEvents.AddTag -> scope.launch {
                val dataState = tagUseCase.addTag.invoke(event.tag)
                if (dataState is DataState.Error) event.onDone(dataState.exception)

                if (dataState is DataState.Success) {
                    event.onDone(null)
                    onEvent(HomeScreenEvents.LoadTags)
                }
            }

            is HomeScreenEvents.DeleteResearch -> scope.launch {
                val dataState = researchUseCase.deleteResearch.invoke(event.model.path)
                if (dataState is DataState.Error) event.onDone(dataState.exception)
                if (dataState is DataState.Success) event.onDone(null)
                loadData()
            }


            is HomeScreenEvents.LoadStudentProfile -> loadUserProfile(event.userId)
            is HomeScreenEvents.SendNotification -> scope.launch {
                val dataState = researchUseCase.sendNotificationUseCase.invoke(
                    topic = Topics.ResearchPosted.name,
                    model = createNotification(
                        event.title,
                        event.researchId,
                        event.imageLink,
                        created = event.created
                    )
                )
                if (dataState is DataState.Error) {
                    event.onDone.invoke(dataState.exception.message)
                    return@launch
                }
                event.onDone.invoke(null)
            }

            is HomeScreenEvents.OnTitleAndImageUrlChange -> {
                _titleAndUrl.value = event.title to event.imageLink
            }
        }
    }

    private fun createNotification(
        title: String,
        researchId: String,
        imageUrl: String?,
        created: Long
    ) = NotificationModel(
        message = Message(
            topic = "no_topic", notification = Notification(
                title = "New Research Opportunity", body = title
            ), data = Data(
                key = researchId, created = created.toString(), image = imageUrl
            )
        )
    )

    private fun loadUserProfile(uid: String) = scope.launch {
        _userProfile.value = getUseDetailUseCase.invoke(uid)
    }

    private fun loadTags() = scope.launch {
        _allTags.value = tagUseCase.getAllTags.invoke()
    }

    private fun loadData() = scope.launch {
        _researchModel.value = researchUseCase.getAllPosts.invoke(_userId.value)
    }
}