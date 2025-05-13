package com.example.randomuserapp.repository

import android.util.Log
import com.example.randomuserapp.data.api.RetrofitInstance
import com.example.randomuserapp.data.db.UserDao
import com.example.randomuserapp.data.db.UserEntity

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun getUsers(): List<UserEntity> {
        return try {
            val response = RetrofitInstance.api.getUsers()
            val users = response.results.map {
                UserEntity(
                    firstName = it.name.first,
                    lastName = it.name.last,
                    email = it.email,
                    avatarUrl = it.picture.large
                )
            }

            userDao.deleteAllUsers()
            userDao.insertUsers(users)

            users
        } catch (e: Exception) {
            Log.e("UserRepository", "Ошибка при получении пользователей", e)
            userDao.getAllUsers()
        }
    }
}



