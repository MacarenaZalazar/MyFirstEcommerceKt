package com.example.toramarket.data.remote.dto

import com.google.gson.annotations.*

data class UserRegisterDto(val fullName: String, val email: String, val encryptedPassword: String)
data class UserDto(
    val email: String,
    val fullName: String,
    val encryptedPassword: String,
    val userImageUrl: String?
)

data class LoginResponse(
    val message: String,
    val user: LoggerUserDto
)

data class LoggerUserDto(
    @SerializedName("_id")
    val id: String,
    val email: String,
    val fullName: String,
    val encryptedPassword: String,
    val userImageUrl: String?,
)