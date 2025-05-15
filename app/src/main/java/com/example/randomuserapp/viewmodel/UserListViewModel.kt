package com.example.randomuserapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.data.db.UserEntity
import com.example.randomuserapp.repository.UserRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserListViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userList = MutableStateFlow<PersistentList<UserEntity>>(persistentListOf())
    val userList = _userList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private var currentPage = 1
    private var isLoadingPage = false

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoadingPage) return
        isLoadingPage = true
        _isLoading.update { true }

        viewModelScope.launch(Dispatchers.IO) {
            val newUsers = repository.getUsers(currentPage)

            _userList.update { currentList ->
                (currentList + newUsers).toPersistentList()
            }
        }

            currentPage++
            _isLoading.update { false }
            isLoadingPage = false
        }
    }
