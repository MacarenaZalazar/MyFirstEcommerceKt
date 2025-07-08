package com.example.myfirstecommercekt.data.remote.api

import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.*
import retrofit2.http.*

interface UserService {
    @POST("users/register")
    suspend fun register(@Body request: UserRegisterDto): UserDto

    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserDto>

    @PUT("users/{email}")
    suspend fun updateProfile(
        @Path("email") email: String,
        @Body request: UserDto
    ): Response<UserDto>
}