package com.example.toramarket.data.remote.api

import com.example.toramarket.data.remote.dto.*
import retrofit2.*
import retrofit2.http.*

interface AuthService {
    @POST("users/login")
    suspend fun login(@Body request: AuthRequest): Response<LoginResponse>

}