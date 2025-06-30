package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.*


interface UserRepository {

    suspend fun register(request: UserRegisterDto): UserDto
    suspend fun getUserByEmail(email: String): Response<UserDto>
    suspend fun updateProfile(request: UserDto): Response<UserDto>
}