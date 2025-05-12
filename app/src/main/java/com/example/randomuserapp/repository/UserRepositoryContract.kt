package com.example.randomuserapp.repository

import com.example.randomuserapp.data.db.UserEntity

interface UserRepositoryContract {
    suspend fun getUsers(): List<UserEntity>
}
