package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.remote.api.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import retrofit2.*
import retrofit2.http.*
import javax.inject.*

class UserRepositoryImpl @Inject constructor(private val api: UserApi) : UserRepository {
    override suspend fun register(request: User) = api.register(request)
    override suspend fun updateProfile(@Body request: User): Response<User> =
        api.updateProfile(request)
}