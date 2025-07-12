package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.remote.api.*
import com.example.myfirstecommercekt.data.remote.dto.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import retrofit2.*
import retrofit2.http.*
import javax.inject.*

class UserRepositoryImpl @Inject constructor(private val api: UserService) : UserRepository {
    override suspend fun register(request: UserRegisterDto): UserDto = api.register(request)
    override suspend fun getUserByEmail(email: String): Response<UserDto> =
        api.getUserByEmail(email)

    override suspend fun updateProfile(@Body request: UserDto): Response<UserDto> =
        api.updateProfile(email = request.email, request = request)
}