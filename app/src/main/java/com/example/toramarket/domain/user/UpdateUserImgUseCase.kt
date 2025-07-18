package com.example.toramarket.domain.user

import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.data.repository.interfaces.*
import retrofit2.*
import javax.inject.*

class UpdateUserImgUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(
        email: String,
        image: String
    ): Response<UserDto> {
        var img = UserImgDto(
            image
        )
        return repository.updateProfileImg(email, img)
    }
}