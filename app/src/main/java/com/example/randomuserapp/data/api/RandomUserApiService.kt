package com.example.randomuserapp.data.api

import com.example.randomuserapp.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApiService {
    @GET("api/")
    suspend fun getUsers(@Query("results") count: Int = 20): UserResponse
}
