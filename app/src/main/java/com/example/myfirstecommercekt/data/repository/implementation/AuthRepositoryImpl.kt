package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.remote.api.*
import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.*
import javax.inject.*

class AuthRepositoryImpl @Inject constructor(private val api: AuthApi) {
    suspend fun login(request: AuthRequest): Response<User> = api.login(request)
}