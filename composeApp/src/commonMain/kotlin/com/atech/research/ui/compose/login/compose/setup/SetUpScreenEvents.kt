package com.atech.research.ui.compose.login.compose.setup

import com.atech.research.core.ktor.model.SuccessResponse
import com.atech.research.core.ktor.model.UserType
import com.atech.research.utils.DataState

/**
 * Set up screen events
 * This is the sealed interface that represents the events in the set up screen.
 */
sealed interface SetUpScreenEvents {
    /**
     * Set uid event
     * This event is called when the uid is set.
     * @param uid The uid
     */
    data class SetUid(val uid: String) : SetUpScreenEvents

    /**
     * On password changed
     * This event is called when the password is changed.
     * @property password The password
     * @constructor Create empty On password changed
     */
    data class OnPasswordChanged(val password: String) : SetUpScreenEvents

    /**
     * Save changes
     * This event is called when the changes are saved.
     * @property action The action
     * @constructor Create empty Save changes
     */
    data class SaveChanges(val action: (DataState<SuccessResponse>) -> Unit) : SetUpScreenEvents

    /**
     * On user type change
     * This event is called when the user type is changed.
     * @property userType The user type
     * @constructor Create empty On user type change
     */
    data class OnUserTypeChange(val userType: UserType) : SetUpScreenEvents
}