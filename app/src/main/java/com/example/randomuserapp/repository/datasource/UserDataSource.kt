package com.example.randomuserapp.repository.datasource

import com.example.randomuserapp.data.db.UserEntity

interface UserDataSource {
    suspend fun getUsers(page: Int): List<UserEntity>
    suspend fun getUserById(id: Int): UserEntity?
    suspend fun clearUsers()
}
