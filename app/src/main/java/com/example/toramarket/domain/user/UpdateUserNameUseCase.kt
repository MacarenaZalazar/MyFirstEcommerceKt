package com.example.toramarket.domain.user

import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.domain.repository.*
import retrofit2.*
import javax.inject.*

class UpdateUserNameUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(
        email: String,
        name: String,
    ): Response<UserDto> {
        var user = UpdateNameDto(
            name,
        )

        return repository.updateName(email, user)
    }
}