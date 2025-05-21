package com.example.randomuserapp.repository

import android.util.Log
import com.example.randomuserapp.data.db.UserEntity
import com.example.randomuserapp.di.qualifier.LocalDataSource
import com.example.randomuserapp.di.qualifier.RemoteDataSource
import com.example.randomuserapp.repository.datasource.UserDataSource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: UserDataSource,
    @LocalDataSource private val localDataSource: UserDataSource
) : UserRepository {

    override suspend fun getUsers(page: Int): List<UserEntity> {
        return try {
            val users = remoteDataSource.getUsers(page)

            if (page == 1) {
                localDataSource.clearUsers()
            }

            localDataSource.insertUsers(users)
            users
        } catch (e: Exception) {
            Log.e("UserRepository", "Ошибка при получении пользователей", e)
            localDataSource.getUsers(page)
        }
    }

    override suspend fun getUserById(id: Int): UserEntity? {
        return localDataSource.getUserById(id)
    }
}
