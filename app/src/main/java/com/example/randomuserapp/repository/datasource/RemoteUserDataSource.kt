package com.example.randomuserapp.repository.datasource

import com.example.randomuserapp.data.api.RandomUserApiService
import com.example.randomuserapp.data.db.UserEntity
import javax.inject.Inject

class RemoteUserDataSource @Inject constructor(
    private val apiService: RandomUserApiService
) : UserDataSource {

    override suspend fun getUsers(page: Int): List<UserEntity> {
        val response = apiService.getUsers(page = page)
        return response.results.mapIndexed { index, dto ->
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
    }

    override suspend fun getUserById(id: Int): UserEntity? {
        throw UnsupportedOperationException("Remote source does not support getUserById")
    }

    override suspend fun clearUsers() {
        throw UnsupportedOperationException("Remote source does not support clearUsers")
    }

    override suspend fun insertUsers(users: List<UserEntity>) {
        throw UnsupportedOperationException("Remote source does not support insertUsers")
    }
}
