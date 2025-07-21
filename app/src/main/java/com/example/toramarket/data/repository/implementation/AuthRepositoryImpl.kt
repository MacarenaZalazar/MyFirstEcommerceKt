package com.example.toramarket.data.repository.implementation

import com.example.toramarket.data.remote.api.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.domain.repository.*
import retrofit2.*
import javax.inject.*

class AuthRepositoryImpl @Inject constructor(private val api: AuthService) : AuthRepository {
    override suspend fun login(request: AuthRequest): Response<LoginResponse> = api.login(request)
}