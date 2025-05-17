package com.example.randomuserapp.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.core.content.edit
import com.example.randomuserapp.data.api.RetrofitInstance
import com.example.randomuserapp.data.db.UserDao
import com.example.randomuserapp.data.db.UserEntity
import kotlinx.coroutines.*
import java.util.*

class UserRepositoryImpl(
    private val userDao: UserDao, private val context: Context
) : UserRepository {

    private val sharedPrefs by lazy {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    private var sessionId: String = ""

    init {
        val hasInternet = hasInternet(context)

        if (hasInternet) {
            sessionId = UUID.randomUUID().toString()
            sharedPrefs.edit {
                putString("CURRENT_SESSION_ID", sessionId)
            }

            CoroutineScope(Dispatchers.IO).launch {
                userDao.deleteAllUsers()
            }

        } else {
            sessionId = sharedPrefs.getString("CURRENT_SESSION_ID", "") ?: ""
        }
    }

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
                    phone = dto.phone,
                    sessionId = sessionId
                )
            }

            userDao.insertUsers(users)
            users
        } catch (e: Exception) {
            Log.e("UserRepository", "Ошибка при получении пользователей", e)

            val offset = (page - 1) * 20
            userDao.getUsersBySession(sessionId, limit = 20, offset = offset)
        }
    }

    override suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }

    private fun hasInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        ))
    }
}
