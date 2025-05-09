package com.example.randomuserapp.data.model

data class UserResponse(
    val results: List<UserDto>
)

data class UserDto(
    val name: NameDto,
    val email: String,
    val picture: PictureDto
)

data class NameDto(
    val first: String,
    val last: String
)

data class PictureDto(
    val large: String
)
