package com.example.randomuserapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: UserRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val userId: Int = checkNotNull(savedStateHandle["userId"])

    private val _state = MutableStateFlow(UserDetailState())
    val state: StateFlow<UserDetailState> = _state

    fun loadUser(userId: Int) {
        viewModelScope.launch {
            val user = repository.getUserById(userId)
            _state.value = state.value.copy(user = user)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUserById(userId)
            _state.update { it.copy(isLoading = false, user = user) }
        }
    }
}