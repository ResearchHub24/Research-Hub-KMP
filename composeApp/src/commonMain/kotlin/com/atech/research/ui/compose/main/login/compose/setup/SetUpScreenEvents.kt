package com.atech.research.ui.compose.main.login.compose.setup

import com.atech.research.core.model.UserType

sealed interface SetUpScreenEvents {
    data class SetUid(val uid: String) : SetUpScreenEvents
    data class OnPasswordChanged(val password: String) : SetUpScreenEvents
    data object SetPassword : SetUpScreenEvents
    data class OnUserTypeChange(val userType : UserType) : SetUpScreenEvents
}