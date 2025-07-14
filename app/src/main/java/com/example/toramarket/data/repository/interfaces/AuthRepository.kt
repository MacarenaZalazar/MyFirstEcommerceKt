package com.example.toramarket.data.repository.interfaces

import com.example.toramarket.data.remote.dto.*
import retrofit2.*

interface AuthRepository {
    suspend fun login(request: AuthRequest): Response<LoginResponse>
}