package com.atech.research.ui.compose.main.login.compose.setup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.research.core.ktor.ResearchHubClient
import com.atech.research.core.model.UserModel
import com.atech.research.core.model.UserType
import com.atech.research.utils.DataState
import kotlinx.coroutines.launch

class SetUpViewModel(
    private val client: ResearchHubClient
) : ViewModel() {
    private var uid: String = ""
    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _userType = mutableStateOf(UserType.STUDENT)
    val userType: State<UserType> get() = _userType

    private val _isPasswordValid = mutableStateOf(false)
    val isPasswordValid: State<Boolean> get() = _isPasswordValid

    private val _user = mutableStateOf<DataState<UserModel>>(DataState.Loading)
    val user: State<DataState<UserModel>> get() = _user

    fun onEvent(event: SetUpScreenEvents) {
        when (event) {
            is SetUpScreenEvents.OnPasswordChanged -> {
                if (_password.value.isBlank()) _isPasswordValid.value = false
                _password.value = event.password
                _isPasswordValid.value = isPasswordValid(event.password)
            }

            is SetUpScreenEvents.OnUserTypeChange -> _userType.value = event.userType


            SetUpScreenEvents.SetPassword -> {}
            is SetUpScreenEvents.SetUid -> {
                uid = event.uid
                getUser()
            }
        }
    }

    private fun getUser() = viewModelScope.launch {
        _user.value = DataState.Loading
        try {
            val response = client.getUser(uid)
            _user.value = response
        } catch (e: Exception) {
            _user.value = DataState.Error(e)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        val minLength = 8
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        val isLongEnough = password.length >= minLength

        return hasUpperCase && hasDigit && hasSpecialChar && isLongEnough
    }

}