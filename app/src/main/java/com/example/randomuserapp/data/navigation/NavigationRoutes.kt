package com.example.randomuserapp.data.navigation

import kotlinx.serialization.Serializable

@Serializable
object UserList

@Serializable
data class UserDetail(val userId: Int)
