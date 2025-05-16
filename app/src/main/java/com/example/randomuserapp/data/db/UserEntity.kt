package com.example.randomuserapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "first_name") val firstName: String,

    @ColumnInfo(name = "last_name") val lastName: String,

    @ColumnInfo(name = "email") val email: String,

    @ColumnInfo(name = "avatar_url") val avatarUrl: String,

    @ColumnInfo(name = "birth_date") val birthDate: String,

    @ColumnInfo(name = "age") val age: Int,

    @ColumnInfo(name = "street") val street: String,

    @ColumnInfo(name = "city") val city: String,

    @ColumnInfo(name = "country") val country: String,

    @ColumnInfo(name = "phone") val phone: String
)