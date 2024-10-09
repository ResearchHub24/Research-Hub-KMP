package com.atech.research.ui.compose.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.EducationDetails
import com.atech.research.core.ktor.model.LinkModel
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.ktor.model.UserUpdateQueryHelper
import com.atech.research.core.usecase.UserUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchHubViewModel
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.researchHubLog
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val useCases: UserUseCases, private val pref: PrefManager
) : ResearchHubViewModel() {

    private val _user = mutableStateOf<DataState<UserModel>>(DataState.Loading)
    val user: State<DataState<UserModel>> get() = _user

    private val _currentEducationDetails = mutableStateOf<EducationDetails?>(null)
    val currentEducationDetails: State<EducationDetails?> get() = _currentEducationDetails

    private val _currentLinkClick = mutableStateOf<LinkModel?>(null)
    val currentLinkClick: State<LinkModel?> get() = _currentLinkClick

    init {
        onEvent(ProfileEvents.LoadData)
    }

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.LoadData -> loadUser()


            is ProfileEvents.OnEditClick -> {
                _currentEducationDetails.value = event.model
            }

            is ProfileEvents.OnEducationEdit -> {
                _currentEducationDetails.value = event.model
            }

            is ProfileEvents.OnSaveEducationClick -> scope.launch {
                if (_currentEducationDetails.value != null) {
                    val updatedList =
                        if (_currentEducationDetails.value!!.created == null) event.educationDetailsList + _currentEducationDetails.value!!.copy(
                            created = System.currentTimeMillis()
                        )
                        else {
                            event.educationDetailsList.map {
                                if (it.created == _currentEducationDetails.value!!.created) _currentEducationDetails.value!!
                                else it
                            }
                        }
                    updateEducationDetails(updatedList, onError = event.onComplete)
                }
            }

            is ProfileEvents.OnDeleteEducationClick -> scope.launch {
                val updatedList = event.educationDetailsList.filter {
                    it.created != event.yetToDelete.created
                }
                updateEducationDetails(updatedList, onError = event.onComplete)
            }

            is ProfileEvents.OnLinkClick ->
                _currentLinkClick.value = event.link


            is ProfileEvents.OnAddLinkClick -> {
                val updateLinkList = when (event.link.created) {
                    0L -> event.linkList + event.link.copy(created = System.currentTimeMillis())
                    else -> event.linkList.map {
                        if (it.created == event.link.created) event.link
                        else it
                    }
                }
                updateEducationDetails(updatedLinks = updateLinkList, onError = event.onComplete)
            }

            is ProfileEvents.OnDeleteLinkClick -> {
                val updatedLinkList = event.linkList.filter {
                    it.created != event.yetToDelete.created
                }
                updateEducationDetails(updatedLinks = updatedLinkList, onError = event.onComplete)
            }
        }
    }

    private fun loadUser() = scope.launch {
        _user.value = useCases.getUserDetail(pref.getString(Prefs.USER_ID.name))
    }

    private fun updateEducationDetails(
        updatedList: List<EducationDetails> = emptyList(),
        updatedLinks: List<LinkModel>? = null,
        onError: (String?) -> Unit = {}
    ) = scope.launch {
        try {
            val dataState = useCases.updateUserDetail(
                pref.getString(Prefs.USER_ID.name),
                if (updatedLinks != null) UserUpdateQueryHelper.UpdateUserLinks(updatedLinks)
                else UserUpdateQueryHelper.UpdateUserEducationDetails(updatedList)
            )
            if (dataState is DataState.Success) {
                _currentEducationDetails.value = null
                loadUser()
                onError(null)
            }
            if (dataState is DataState.Error) {
                onError(dataState.exception.message)
            }
        } catch (e: Exception) {
            researchHubLog(ResearchLogLevel.ERROR, "Error: $e")
        }
    }
}