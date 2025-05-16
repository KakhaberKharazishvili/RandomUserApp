package com.example.randomuserapp.viewmodel

import com.example.randomuserapp.data.db.UserEntity

data class UserDetailState(
    val isLoading: Boolean = true, val user: UserEntity? = null
)