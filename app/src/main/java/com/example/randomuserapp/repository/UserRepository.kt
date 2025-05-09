package com.example.randomuserapp.repository

import com.example.randomuserapp.data.api.RetrofitInstance
import com.example.randomuserapp.data.db.UserDao
import com.example.randomuserapp.data.db.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun getUsers(): List<UserEntity> {
        return withContext(Dispatchers.IO) {
            try {
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
                userDao.getAllUsers()
            }
        }
    }
}
