package com.atech.research.ui.compose.teacher.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.TagModel
import com.atech.research.core.usecase.ResearchUseCase
import com.atech.research.core.usecase.TagUseCase
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val researchUseCase: ResearchUseCase,
    private val tagUseCase: TagUseCase,
) : ResearchHubViewModel() {

    private val _userId = mutableStateOf("")
    private val _researchModel = mutableStateOf<DataState<List<ResearchModel>>>(DataState.Loading)
    val allResearch: State<DataState<List<ResearchModel>>> get() = _researchModel

    private val _currentResearchModel = mutableStateOf<ResearchModel?>(null)
    val currentResearchModel: State<ResearchModel?> get() = _currentResearchModel

    private val _allTags = mutableStateOf<DataState<List<TagModel>>>(DataState.Loading)
    val allTags: State<DataState<List<TagModel>>> get() = _allTags

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
            is HomeScreenEvents.SaveChanges ->
                _currentResearchModel.value?.let {
                    scope.launch {
                        researchUseCase.updateOrPostResearch.invoke(it)
                        loadData()
                        event.onDone()
                    }
                }


            HomeScreenEvents.LoadTags -> loadTags()

            is HomeScreenEvents.AddTag -> scope.launch {
                val dataState = tagUseCase.addTag.invoke(event.tag)
                if (dataState is DataState.Error)
                    event.onDone(dataState.exception)

                if (dataState is DataState.Success) {
                    event.onDone(null)
                    onEvent(HomeScreenEvents.LoadTags)
                }
            }
        }
    }

    private fun loadTags() =
        scope.launch {
            _allTags.value = tagUseCase.getAllTags.invoke()
        }

    private fun loadData() = scope.launch {
        _researchModel.value = researchUseCase.getAllPosts.invoke(_userId.value)
    }
}