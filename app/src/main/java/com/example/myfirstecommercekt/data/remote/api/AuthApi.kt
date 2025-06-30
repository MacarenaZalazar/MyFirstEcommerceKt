package com.example.myfirstecommercekt.data.remote.api

import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.*
import retrofit2.http.*

interface AuthApi {
    @POST("users/login")
    suspend fun login(@Body request: AuthRequest): Response<LoginResponse>

}