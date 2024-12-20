package com.atech.research.ui.compose.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.ApplicationModel
import com.atech.research.core.ktor.model.EducationDetails
import com.atech.research.core.ktor.model.LinkModel
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.ktor.model.UserUpdateQueryHelper
import com.atech.research.core.usecase.ApplyResearchUseCase
import com.atech.research.core.usecase.UserUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchHubViewModel
import com.atech.research.utils.ResearchLogLevel
import com.atech.research.utils.researchHubLog
import kotlinx.coroutines.launch

/**
 * Profile view model
 * This is the profile view model.
 * @property useCases User use cases
 * @property pref Pref manager
 * @property signOutHelper Sign out helper
 * @property applyResearchUseCase Apply research use case
 * @constructor Create empty Profile view model
 * @see UserUseCases
 * @see PrefManager
 * @see SignOutHelper
 * @see ApplyResearchUseCase
 * @see ResearchHubViewModel
 * @see ProfileEvents
 */
class ProfileViewModel(
    private val useCases: UserUseCases,
    private val pref: PrefManager,
    private val signOutHelper: SignOutHelper,
    private val applyResearchUseCase: ApplyResearchUseCase
) : ResearchHubViewModel() {

    private val _user = mutableStateOf<DataState<UserModel>>(DataState.Loading)
    val user: State<DataState<UserModel>> get() = _user

    private val _currentEducationDetails = mutableStateOf<EducationDetails?>(null)
    val currentEducationDetails: State<EducationDetails?> get() = _currentEducationDetails

    private val _currentLinkClick = mutableStateOf<LinkModel?>(null)
    val currentLinkClick: State<LinkModel?> get() = _currentLinkClick

    private val _skillList = mutableStateOf<DataState<List<String>>>(DataState.Loading)
    val skillList: State<DataState<List<String>>> get() = _skillList

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

            is ProfileEvents.OnLinkClick -> _currentLinkClick.value = event.link


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

            is ProfileEvents.PerformSignOut -> scope.launch {
                signOutHelper.signOut { error ->
                    if (error != null) {
                        event.onComplete(error.message)
                    } else {
                        pref.remove(Prefs.USER_ID.name)
                        pref.remove(Prefs.USER_TYPE.name)
                        pref.remove(Prefs.USER_NAME.name)
                        pref.remove(Prefs.SET_PASSWORD_DONE.name)
                        pref.remove(Prefs.USER_EMAIL.name)
                        pref.remove(Prefs.USER_PROFILE_URL.name)
                        event.onComplete(null)
                    }
                }
            }

            ProfileEvents.LoadSkillList -> loadSkill()
            is ProfileEvents.OnAddSkillClick -> {
                val updatedList = event.skillList
                researchHubLog(
                    ResearchLogLevel.DEBUG, "Updated List: $updatedList"
                )
                updateEducationDetails(skillList = updatedList, onError = event.onComplete)
            }

            is ProfileEvents.ApplyResearch -> {
                researchHubLog(
                    ResearchLogLevel.DEBUG,
                    "Apply Research: ${event.researchId}, ${event.answerModelList}"
                )
                val userDetails = _user.value as? DataState.Success ?: return
                val applicationModel = ApplicationModel(
                    researchId = event.researchId,
                    answers = event.answerModelList,
                    userUid = userDetails.data.uid!!,
                    userName = userDetails.data.displayName ?: "No Name",
                    userProfile = userDetails.data.photoUrl ?: "",
                    researchTitle = event.researchTitle,
                    userEmail = userDetails.data.email ?: "No Email",
                )
                applyResearch(applicationModel, event.onComplete)
            }
        }
    }

    private fun loadUser() = scope.launch {
        _user.value = useCases.getUserDetail(pref.getString(Prefs.USER_ID.name))
    }

    private fun updateEducationDetails(
        updatedList: List<EducationDetails> = emptyList(),
        updatedLinks: List<LinkModel>? = null,
        skillList: List<String>? = null,
        onError: (String?) -> Unit = {}
    ) = scope.launch {
        try {
            val dataState = useCases.updateUserDetail(
                pref.getString(Prefs.USER_ID.name),
                when {
                    updatedLinks != null -> UserUpdateQueryHelper.UpdateUserLinks(updatedLinks)
                    skillList != null -> UserUpdateQueryHelper.UpdateUserSkillList(skillList)
                    else -> UserUpdateQueryHelper.UpdateUserEducationDetails(updatedList)
                },
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

    private fun loadSkill() {
        scope.launch {
            val dataState = useCases.getAllSkills()
            _skillList.value = dataState
        }
    }

    private fun applyResearch(

        applicationModel: ApplicationModel,
        onError: (String?) -> Unit = {}
    ) {
        scope.launch {
            val dataState = applyResearchUseCase(applicationModel.researchId, applicationModel)
            if (dataState is DataState.Success) {
                loadUser()
                onError(null)
            }
            if (dataState is DataState.Error) {
                onError(dataState.exception.message)
            }
        }
    }
}