package com.example.myfirstecommercekt.data.remote.dto

data class UserRegisterDto(val fullName: String, val email: String, val encryptedPassword: String)
data class UserDto(
    val email: String,
    val fullName: String,
    val encryptedPassword: String,
    val userImageUrl: String?
)

data class LoginResponse(
    val message: String,
    val user: UserDto
)
