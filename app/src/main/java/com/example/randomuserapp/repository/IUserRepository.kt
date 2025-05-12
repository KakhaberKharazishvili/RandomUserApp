package com.example.randomuserapp.repository

import com.example.randomuserapp.data.db.UserEntity

interface IUserRepository {
    suspend fun getUsers(): List<UserEntity>
}
