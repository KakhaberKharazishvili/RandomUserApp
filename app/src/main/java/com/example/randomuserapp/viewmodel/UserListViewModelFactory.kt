package com.example.randomuserapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randomuserapp.repository.UserRepositoryImpl

class UserListViewModelFactory(private val repository: UserRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
