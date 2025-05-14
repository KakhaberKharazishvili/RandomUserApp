package com.example.randomuserapp.repository

import android.content.Context
import com.example.randomuserapp.data.db.AppDatabase
import com.example.randomuserapp.data.db.UserEntity

interface UserRepository {
    suspend fun getUsers(): List<UserEntity>
    suspend fun getUserById(id: Int): UserEntity?

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepositoryImpl(
                    AppDatabase.getDatabase(context).userDao()
                ).also { INSTANCE = it }
            }
        }
    }
}