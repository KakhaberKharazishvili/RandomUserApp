package com.example.randomuserapp.repository

import android.util.Log
import com.example.randomuserapp.data.api.RetrofitInstance
import com.example.randomuserapp.data.db.UserDao
import com.example.randomuserapp.data.db.UserEntity

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun getUsers(page: Int): List<UserEntity> {
        return try {
            val response = RetrofitInstance.api.getUsers(page = page)
            val users = response.results.map {
                UserEntity(
                    firstName = it.name.first,
                    lastName = it.name.last,
                    email = it.email,
                    avatarUrl = it.picture.large,
                    birthDate = it.dob.date.substring(0, 10),
                    age = it.dob.age,
                    street = "${it.location.street.number} ${it.location.street.name}",
                    city = it.location.city,
                    country = it.location.country,
                    phone = it.phone
                )
            }

            userDao.insertUsers(users)

            return userDao.getAllUsers()
        } catch (e: Exception) {
            Log.e("UserRepository", "Ошибка при получении пользователей", e)
            userDao.getAllUsers()
        }
    }
    override suspend fun getUserById(id: Int): UserEntity? {
        val user = userDao.getUserById(id)
        return user
    }
}



