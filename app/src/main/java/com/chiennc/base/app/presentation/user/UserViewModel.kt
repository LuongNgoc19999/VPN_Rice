package com.chiennc.base.app.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chiennc.base.app.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val getUsersUseCase: GetUsersUseCase) : ViewModel() {
    private val _state = MutableStateFlow<UserState>(UserState.Loading)
    val state: StateFlow<UserState> get() = _state

    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val users = getUsersUseCase()
                _state.value = UserState.Success(users)
            } catch (e: Exception) {
                _state.value = UserState.Error(e.message ?: "Unknown error")
            }
        }
    }
}