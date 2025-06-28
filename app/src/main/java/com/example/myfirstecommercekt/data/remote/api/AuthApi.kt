package com.example.myfirstecommercekt.data.remote.api

interface AuthApi {
    suspend fun login()
    suspend fun register()
    suspend fun updateProfile()
}