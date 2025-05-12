package com.example.randomuserapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.data.db.UserEntity
import com.example.randomuserapp.repository.UserRepositoryImpl
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepositoryImpl) : ViewModel() {

    private val _userList = MutableStateFlow<PersistentList<UserEntity>>(persistentListOf())
    val userList = _userList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            _userList.update { repository.getUsers().toPersistentList() }
            _isLoading.update { false }
        }
    }
}
