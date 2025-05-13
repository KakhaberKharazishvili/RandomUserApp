package com.example.randomuserapp.data.model

data class UserResponse(
    val results: List<UserDto>
)

data class UserDto(
    val name: NameDto,
    val email: String,
    val picture: PictureDto,
    val dob: DobDto,
    val location: LocationDto,
    val phone: String
)

data class NameDto(
    val first: String,
    val last: String
)

data class PictureDto(
    val large: String
)

data class DobDto(
    val date: String,
    val age: Int
)

data class LocationDto(
    val street: StreetDto,
    val city: String,
    val country: String
)

data class StreetDto(
    val number: Int,
    val name: String
)
