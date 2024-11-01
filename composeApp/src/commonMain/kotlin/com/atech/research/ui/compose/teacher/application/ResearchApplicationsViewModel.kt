package com.atech.research.ui.compose.teacher.application

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ApplicationModel
import com.atech.research.core.usecase.ApplicationUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch


class ResearchApplicationsViewModel(
    private val useCases: ApplicationUseCases
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
                    event.userUid,
                    event.action
                )
                if (dataState is DataState.Error) {
                    event.onComplete(dataState.exception.message)
                    return@launch
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