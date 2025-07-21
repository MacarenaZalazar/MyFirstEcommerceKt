package com.example.toramarket.domain.user


import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.domain.repository.*
import retrofit2.*
import javax.inject.*

class UpdateUserPasswordUseCase
@Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Response<UserDto> {
        var user = UpdatePassDto(
            password,
        )
        return repository.updatePassword(email, user)
    }
}