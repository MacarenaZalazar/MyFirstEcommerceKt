package com.example.toramarket.data.remote.api

import com.example.toramarket.data.remote.dto.*
import retrofit2.*
import retrofit2.http.*

interface UserService {
    @POST("users/register")
    suspend fun register(@Body request: UserRegisterDto): UserDto

    @GET("users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): Response<UserDto>

    @PUT("users/{email}/name")
    suspend fun updateName(
        @Path("email") email: String,
        @Body request: UpdateNameDto
    ): Response<UserDto>

    @PUT("users/{email}/password")
    suspend fun updatePassword(
        @Path("email") email: String,
        @Body request: UpdatePassDto
    ): Response<UserDto>

    @PUT("users/{email}/image")
    suspend fun updateProfileImg(
        @Path("email") email: String,
        @Body request: UserImgDto
    ): Response<UserDto>
}