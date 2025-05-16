package com.example.randomuserapp.viewmodel

import com.example.randomuserapp.data.db.UserEntity
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class UserListState(
    val isLoading: Boolean = false, val users: PersistentList<UserEntity> = persistentListOf()
)