package com.example.toramarket.domain.user

import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.data.repository.interfaces.*
import com.example.toramarket.utils.helpers.*
import retrofit2.*
import javax.inject.*

class UpdateUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(
        email: String,
        name: String,
        password: String,
        image: String
    ): Response<UserDto> {
        var user = UserDto(
            email,
            name,
            hashPasswordSHA256(password),
            image
        )

        return repository.updateProfile(user)
    }
}