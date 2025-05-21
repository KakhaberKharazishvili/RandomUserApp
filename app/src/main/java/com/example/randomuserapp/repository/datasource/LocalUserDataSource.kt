package com.example.randomuserapp.repository.datasource

import com.example.randomuserapp.data.db.UserDao
import com.example.randomuserapp.data.db.UserEntity
import javax.inject.Inject

class LocalUserDataSource @Inject constructor(
    private val userDao: UserDao
) : UserDataSource {

    override suspend fun getUsers(page: Int): List<UserEntity> {
        val limit = 20
        val offset = (page - 1) * limit
        return userDao.getUsersPaginated(limit, offset)
    }

    override suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }

    override suspend fun clearUsers() {
        userDao.deleteAllUsers()
    }

    override suspend fun insertUsers(users: List<UserEntity>) {
        userDao.insertUsers(users)
    }
}
