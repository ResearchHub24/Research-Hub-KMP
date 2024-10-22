package com.atech.research.ui.compose.student.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.usecase.GetUseDetailUseCase
import com.atech.research.core.usecase.StudentResearchUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch

class StudentHomeViewModel(
    private val useCases: StudentResearchUseCases,
    private val getUseDetailUseCase: GetUseDetailUseCase,
    private val pref: PrefManager,
) : ResearchHubViewModel() {
    private val _researchModel = mutableStateOf<DataState<List<ResearchModel>>>(DataState.Loading)
    val allResearch: State<DataState<List<ResearchModel>>> get() = _researchModel
    private val _currentModel = mutableStateOf<ResearchModel?>(null)
    val selectedResearch: State<ResearchModel?> get() = _currentModel
    private val _isApplied = mutableStateOf(false)
    val isApplied: State<Boolean> get() = _isApplied

    private val _userProfile = mutableStateOf<DataState<UserModel>>(DataState.Loading)
    val userProfile: State<DataState<UserModel>> get() = _userProfile

    init {
        onEvent(StudentHomeEvents.LoadData)
    }

    fun onEvent(event: StudentHomeEvents) {
        when (event) {
            StudentHomeEvents.LoadData -> loadData()
            is StudentHomeEvents.OnResearchClick -> {
                _currentModel.value = event.model
                if (event.model != null)
                    isApplied(
                        event.model.path,
                        pref.getString(Prefs.USER_ID.name)
                    )
            }

            is StudentHomeEvents.LoadUserProfile -> loadUserProfile(event.userId)
        }
    }

    private fun loadUserProfile(uid: String) = scope.launch {
        _userProfile.value = getUseDetailUseCase.invoke(uid)
    }

    private fun loadData() = scope.launch {
        _researchModel.value = useCases.getAllResearch.invoke()
    }

    private fun isApplied(
        researchId: String,
        userId: String
    ) = scope.launch {
        val dataState = useCases.isAppliedToResearchUseCase.invoke(researchId, userId)
        _isApplied.value = dataState is DataState.Success && dataState.data
    }
}