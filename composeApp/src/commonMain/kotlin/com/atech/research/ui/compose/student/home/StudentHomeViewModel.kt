package com.atech.research.ui.compose.student.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ResearchModel
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.usecase.GetResearchById
import com.atech.research.core.usecase.GetUseDetailUseCase
import com.atech.research.core.usecase.StudentResearchUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch

/**
 * Student home view model
 * This view model is used to handle the student home screen
 * @property useCases Student research use cases
 * @property getUseDetailUseCase Get use detail use case
 * @property getResearchById Get research by id
 * @property pref PrefManager
 * @constructor Create empty Student home view model
 * @see StudentResearchUseCases
 * @see GetUseDetailUseCase
 * @see GetResearchById
 * @see PrefManager
 * @see ResearchHubViewModel
 * @see ResearchModel
 * @see UserModel
 * @see DataState
 * @see StudentHomeEvents
 */
class StudentHomeViewModel(
    private val useCases: StudentResearchUseCases,
    private val getUseDetailUseCase: GetUseDetailUseCase,
    private val getResearchById: GetResearchById,
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
            is StudentHomeEvents.SetResearchFromDeepLink -> scope.launch {
                val dataState = getResearchById.invoke(event.researchPath)
                if (dataState is DataState.Error) {
                    event.onComplete(true)
                    return@launch
                }
                if (dataState is DataState.Success) {
                    _currentModel.value = dataState.data
                    event.onComplete(false)
                    return@launch
                }
            }
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