package com.atech.research.ui.compose.teacher.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.usecase.ResearchUseCase
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val researchUseCase: ResearchUseCase,
    private val userId: String
) : ResearchHubViewModel() {

    private val _researchModel = mutableStateOf<DataState<List<ResearchModel>>>(DataState.Loading)
    val allResearch: State<DataState<List<ResearchModel>>> get() = _researchModel

    private val _currentResearchModel = mutableStateOf<ResearchModel?>(null)
    val currentResearchModel: State<ResearchModel?> get() = _currentResearchModel

    init {
        onEvent(HomeScreenEvents.RefreshData)
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.RefreshData -> loadData()
            is HomeScreenEvents.SetResearch -> _currentResearchModel.value = event.model
            is HomeScreenEvents.OnEdit -> _currentResearchModel.value = event.model
            is HomeScreenEvents.SaveChanges -> {
                _currentResearchModel.value?.let {
                    scope.launch {
                        researchUseCase.updateOrPostResearch.invoke(it)
                        loadData()
                        event.onDone()
                    }
                }
            }
        }
    }

    private fun loadData() = scope.launch {
        _researchModel.value = researchUseCase.getAllPosts.invoke(userId)
    }
}