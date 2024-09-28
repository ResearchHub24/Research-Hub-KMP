package com.atech.research.ui.compose.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.usecase.UserUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.PrefManager
import com.atech.research.utils.Prefs
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val useCases: UserUseCases,
    private val pref: PrefManager
) : ResearchHubViewModel() {

    private val _user = mutableStateOf<DataState<UserModel>>(DataState.Loading)
    val user: State<DataState<UserModel>> get() = _user

    init {
        onEvent(ProfileTestCases.LoadData)
    }

    fun onEvent(event: ProfileTestCases) {
        when (event) {
            is ProfileTestCases.LoadData -> {
                loadUser()
            }
        }
    }

    private fun loadUser() = scope.launch {
        _user.value = useCases.getUserDetail(pref.getString(Prefs.USER_ID.name))
    }
}