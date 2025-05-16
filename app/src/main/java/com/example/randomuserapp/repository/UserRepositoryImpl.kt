package com.example.randomuserapp.repository

import android.util.Log
import com.example.randomuserapp.data.api.RetrofitInstance
import com.example.randomuserapp.data.db.UserDao
import com.example.randomuserapp.data.db.UserEntity

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun getUsers(page: Int): List<UserEntity> {
        return try {
            val response = RetrofitInstance.api.getUsers(page = page)
            val users = response.results.mapIndexed { index, dto ->
                UserEntity(
                    id = (page - 1) * 20 + index + 1,
                    firstName = dto.name.first,
                    lastName = dto.name.last,
                    email = dto.email,
                    avatarUrl = dto.picture.large,
                    birthDate = dto.dob.date.substring(0, 10),
                    age = dto.dob.age,
                    street = "${dto.location.street.number} ${dto.location.street.name}",
                    city = dto.location.city,
                    country = dto.location.country,
                    phone = dto.phone
                )
            }

            userDao.insertUsers(users)

            users
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



