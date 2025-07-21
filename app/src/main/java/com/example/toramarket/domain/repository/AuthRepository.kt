package com.example.toramarket.domain.repository

import com.example.toramarket.data.remote.dto.*
import retrofit2.*

interface AuthRepository {
    suspend fun login(request: AuthRequest): Response<LoginResponse>
}