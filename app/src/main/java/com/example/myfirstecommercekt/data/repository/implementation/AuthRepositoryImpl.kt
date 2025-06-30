package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.remote.api.*
import com.example.myfirstecommercekt.data.remote.dto.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import retrofit2.*
import javax.inject.*

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi) : AuthRepository {
    override suspend fun login(request: AuthRequest): Response<LoginResponse> = api.login(request)
}