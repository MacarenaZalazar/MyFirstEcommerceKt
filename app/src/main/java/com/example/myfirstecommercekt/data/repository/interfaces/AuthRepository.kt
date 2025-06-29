package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.data.remote.api.*
import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.*

interface AuthRepository {
    suspend fun login(request: AuthRequest): Response<User>
}