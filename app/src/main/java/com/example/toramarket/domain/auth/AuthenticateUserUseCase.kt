package com.example.toramarket.domain.auth

import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.data.repository.interfaces.*
import retrofit2.*
import javax.inject.*

class AuthenticateUserUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Response<LoginResponse> {
        val dto = AuthRequest(username, password)
        return repository.login(dto)
    }
}