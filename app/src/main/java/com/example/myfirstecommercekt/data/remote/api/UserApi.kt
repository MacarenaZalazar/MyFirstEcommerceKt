package com.example.myfirstecommercekt.data.remote.api

import retrofit2.*
import retrofit2.http.*

interface UserApi {
    suspend fun register(@Body request: User)
    suspend fun updateProfile(@Body request: User): Response<User>
}