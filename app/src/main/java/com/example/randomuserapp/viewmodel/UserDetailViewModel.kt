package com.example.randomuserapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.data.db.UserEntity
import com.example.randomuserapp.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val repository: UserRepository,
    private val userId: Int
) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    init {
        viewModelScope.launch {
            _user.value = repository.getUserById(userId)
        }
    }
}