package com.atech.research.ui.compose.student.faculties

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.atech.research.core.ktor.model.UserModel
import com.atech.research.core.usecase.FacultiesUseCases
import com.atech.research.utils.DataState
import com.atech.research.utils.ResearchHubViewModel
import kotlinx.coroutines.launch


class FacultiesViewModel(
    private val useCases: FacultiesUseCases
) : ResearchHubViewModel() {
    private val _allFaculties = mutableStateOf<DataState<List<UserModel>>>(DataState.Loading)
    val allFaculties: State<DataState<List<UserModel>>> get() = _allFaculties

    private val _selectedUser = mutableStateOf<UserModel?>(null)
    val selectedUser: State<UserModel?> get() = _selectedUser

    init {
        loadFaculties()
    }

    fun onEvent(event: FacultiesEvent) {
        when (event) {
            is FacultiesEvent.LoadFaculties -> loadFaculties()

            is FacultiesEvent.FacultySelected ->
                _selectedUser.value = event.facultyId
        }
    }

    private fun loadFaculties() = scope.launch {
        _allFaculties.value = useCases.invoke()
    }
}