package com.example.toramarket.domain.user

import com.example.toramarket.data.repository.interfaces.*
import javax.inject.*

class IsUserLoggedInUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): Boolean = repository.isUserLoggedIn()
}
