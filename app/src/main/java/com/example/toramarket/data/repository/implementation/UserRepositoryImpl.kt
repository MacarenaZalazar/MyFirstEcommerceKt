package com.example.toramarket.data.repository.implementation

import com.example.toramarket.data.local.*
import com.example.toramarket.data.remote.api.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.domain.repository.*
import kotlinx.coroutines.flow.*
import retrofit2.*
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

    override suspend fun updateName(email: String, request: UpdateNameDto): Response<UserDto> =
        api.updateName(email, request)

    override suspend fun updatePassword(email: String, request: UpdatePassDto): Response<UserDto> =
        api.updatePassword(email, request)

    override suspend fun updateProfileImg(email: String, request: UserImgDto): Response<UserDto> =
        api.updateProfileImg(email, request)

    override suspend fun logOut() = local.clearUser()

    override suspend fun isUserLoggedIn(): Boolean = local.isUserLoggedIn()
}