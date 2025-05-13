package com.example.randomuserapp.repository

import android.content.Context
import com.example.randomuserapp.data.db.AppDatabase

object UserRepositoryProvider {
    private var instance: UserRepository? = null

    fun getRepository(context: Context): UserRepository {
        if (instance == null) {
            val db = AppDatabase.getDatabase(context)
            instance = UserRepositoryImpl(db.userDao())
        }
        return instance!!
    }
}
