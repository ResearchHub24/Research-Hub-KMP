package com.atech.research.ui.compose.student.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.usecase.StudentResearchUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch

class StudentHomeViewModel(
    private val useCases: StudentResearchUseCases
) : ResearchHubViewModel() {
    private val _researchModel = mutableStateOf<DataState<List<ResearchModel>>>(DataState.Loading)
    val allResearch: State<DataState<List<ResearchModel>>> get() = _researchModel
    private val _currentModel = mutableStateOf<ResearchModel?>(null)
    val selectedResearch: State<ResearchModel?> get() = _currentModel

    init {
        onEvent(StudentHomeEvents.LoadData)
    }

    fun onEvent(event: StudentHomeEvents) {
        when (event) {
            StudentHomeEvents.LoadData -> loadData()
            is StudentHomeEvents.OnResearchClick -> _currentModel.value = event.model
        }
    }

    private fun loadData() = scope.launch {
        _researchModel.value = useCases.getAllResearch.invoke()
    }
}