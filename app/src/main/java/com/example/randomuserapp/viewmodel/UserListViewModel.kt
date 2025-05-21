package com.example.randomuserapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuserapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(UserListState())
    val state: StateFlow<UserListState> = _state

    private val pageSize = 20

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        if (_state.value.isLoading) return

        val currentPage = _state.value.users.size / pageSize + 1

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            val newUsers = repository.getUsers(currentPage)

            _state.update {
                it.copy(
                    isLoading = false, users = (it.users + newUsers).toPersistentList()
                )
            }
        }
    }
}
