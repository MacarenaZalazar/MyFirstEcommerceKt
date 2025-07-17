package com.example.toramarket.data.repository.interfaces

import com.example.toramarket.data.remote.dto.*
import retrofit2.*


interface UserRepository {

    suspend fun isUserLoggedIn(): Boolean
    suspend fun register(request: UserRegisterDto): UserDto
    suspend fun getUserEmail(): String
    suspend fun getUserId(): String?
    suspend fun getUserByEmail(email: String): Response<UserDto>
    suspend fun updateProfile(request: UserDto): Response<UserDto>
    suspend fun logOut()
}