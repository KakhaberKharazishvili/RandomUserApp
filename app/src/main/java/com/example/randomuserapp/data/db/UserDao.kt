package com.example.randomuserapp.data.db

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM users WHERE session_id = :sessionId LIMIT :limit OFFSET :offset")
    suspend fun getUsersBySession(sessionId: String, limit: Int, offset: Int): List<UserEntity>
}
