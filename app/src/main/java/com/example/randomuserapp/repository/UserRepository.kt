package com.example.randomuserapp.repository

import com.example.randomuserapp.data.db.UserEntity

interface UserRepository {
    suspend fun getUsers(): List<UserEntity>
}
