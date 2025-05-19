package com.example.randomuserapp.repository

import android.content.Context
import com.example.randomuserapp.data.db.UserEntity

interface UserRepository {
    suspend fun getUsers(page: Int): List<UserEntity>
    suspend fun getUserById(id: Int): UserEntity?

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: run {
                    val db = com.example.randomuserapp.data.db.AppDatabase.getDatabase(context)
                    val userDao = db.userDao()

                    val localDataSource =
                        com.example.randomuserapp.repository.datasource.LocalUserDataSource(
                            userDao
                        )
                    val remoteDataSource =
                        com.example.randomuserapp.repository.datasource.RemoteUserDataSource(
                            com.example.randomuserapp.data.api.RetrofitInstance.api
                        )

                    UserRepositoryImpl(remoteDataSource, localDataSource).also {
                        INSTANCE = it
                    }
                }
            }
        }
    }
}