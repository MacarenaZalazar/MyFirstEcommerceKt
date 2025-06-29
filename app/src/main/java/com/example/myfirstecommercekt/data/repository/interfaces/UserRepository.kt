package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.data.remote.api.*
import retrofit2.*
import retrofit2.http.*

interface UserRepository {
    suspend fun register(request: User)
    suspend fun updateProfile(@Body request: User): Response<User>
}