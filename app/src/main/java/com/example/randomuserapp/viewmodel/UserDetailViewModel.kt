package com.example.randomuserapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDetailViewModel @AssistedInject constructor(
    private val repository: UserRepository, @Assisted private val userId: Int
) : ViewModel() {

    private val _state = MutableStateFlow(UserDetailState())
    val state: StateFlow<UserDetailState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getUserById(userId)
            _state.update { it.copy(isLoading = false, user = user) }
        }
    }
}
