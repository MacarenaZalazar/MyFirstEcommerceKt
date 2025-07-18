package com.example.toramarket.data.repository.implementation

import com.example.toramarket.data.local.*
import com.example.toramarket.data.remote.api.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.data.repository.interfaces.*
import kotlinx.coroutines.flow.*
import retrofit2.*
import retrofit2.http.*
import javax.inject.*

class UserRepositoryImpl @Inject constructor(
    private val api: UserService,
    private val local: UserDataStore
) : UserRepository {
    override suspend fun register(request: UserRegisterDto): UserDto = api.register(request)
    override suspend fun getUserEmail(): String = local.userEmail.first().toString()
    override suspend fun getUserId(): String? = local.userId.first()
    override suspend fun getUserByEmail(email: String): Response<UserDto> =
        api.getUserByEmail(email)

    override suspend fun updateProfile(@Body request: UserDto): Response<UserDto> =
        api.updateProfile(email = request.email, request = request)

    override suspend fun updateProfileImg(email: String, request: UserImgDto): Response<UserDto> =
        api.updateProfileImg(email, request)

    override suspend fun logOut() = local.clearUser()

    override suspend fun isUserLoggedIn(): Boolean = local.isUserLoggedIn()
}