package com.atech.research.ui.compose.student.application

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ApplicationModel
import com.atech.research.core.usecase.AllApplicationsUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch

class ApplicationViewModel(
    private val prefManager: PrefManager,
    private val getAllApplications: AllApplicationsUseCases
) : ResearchHubViewModel() {

    private val _allApplications =
        mutableStateOf<DataState<List<ApplicationModel>>>(DataState.Loading)
    val allApplications: State<DataState<List<ApplicationModel>>> get() = _allApplications

    private val _selectedApplication = mutableStateOf<ApplicationModel?>(null)
    val selectedApplication: State<ApplicationModel?> get() = _selectedApplication


    init {
        onEvent(ApplicationEvents.LoadApplication)
    }

    fun onEvent(event: ApplicationEvents) {
        when (event) {
            is ApplicationEvents.LoadApplication -> loadApplications()

            is ApplicationEvents.ApplicationSelected ->
                _selectedApplication.value = event.applicationId
        }
    }

    private fun loadApplications() = scope.launch {
        _allApplications.value =
            getAllApplications.invoke(prefManager.getString(Prefs.USER_ID.name))
    }
}