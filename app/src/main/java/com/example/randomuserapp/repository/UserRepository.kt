package com.example.randomuserapp.repository

import android.content.Context
import com.example.randomuserapp.data.api.RetrofitInstance
import com.example.randomuserapp.data.db.AppDatabase
import com.example.randomuserapp.data.db.UserEntity
import com.example.randomuserapp.repository.datasource.LocalUserDataSource
import com.example.randomuserapp.repository.datasource.RemoteUserDataSource

interface UserRepository {
    suspend fun getUsers(page: Int): List<UserEntity>
    suspend fun getUserById(id: Int): UserEntity?

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: run {
                    val db = AppDatabase.getDatabase(context)
                    val userDao = db.userDao()

                    val localDataSource = LocalUserDataSource(userDao)
                    val remoteDataSource = RemoteUserDataSource(RetrofitInstance.api)

                    UserRepositoryImpl(remoteDataSource, localDataSource).also {
                        INSTANCE = it
                    }
                }
            }
        }
    }
}