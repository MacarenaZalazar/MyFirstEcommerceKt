package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.*
import retrofit2.http.*

interface UserRepository {
    @POST("users/register")
    suspend fun register(request: UserRegisterDto): UserDto
    suspend fun updateProfile(@Body request: UserDto): Response<UserDto>
}