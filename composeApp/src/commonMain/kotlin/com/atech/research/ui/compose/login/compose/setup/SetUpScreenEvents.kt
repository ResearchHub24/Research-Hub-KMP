package com.atech.research.ui.compose.login.compose.setup

import com.atech.research.core.ktor.model.SuccessResponse
import com.atech.research.core.ktor.model.UserType
import com.atech.research.utils.DataState

sealed interface SetUpScreenEvents {
    data class SetUid(val uid: String) : SetUpScreenEvents
    data class OnPasswordChanged(val password: String) : SetUpScreenEvents
    data class SaveChanges(val action: (DataState<SuccessResponse>) -> Unit) : SetUpScreenEvents
    data class OnUserTypeChange(val userType: UserType) : SetUpScreenEvents
}