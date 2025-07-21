package com.example.toramarket.domain.repository

import com.example.toramarket.data.remote.dto.*
import retrofit2.*


interface UserRepository {

    suspend fun isUserLoggedIn(): Boolean
    suspend fun register(request: UserRegisterDto): UserDto
    suspend fun getUserEmail(): String
    suspend fun getUserId(): String?
    suspend fun getUserByEmail(email: String): Response<UserDto>
    suspend fun updateName(email: String, request: UpdateNameDto): Response<UserDto>
    suspend fun updatePassword(email: String, request: UpdatePassDto): Response<UserDto>
    suspend fun updateProfileImg(email: String, request: UserImgDto): Response<UserDto>
    suspend fun logOut()

}