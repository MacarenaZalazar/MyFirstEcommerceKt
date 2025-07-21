package com.example.toramarket.domain.cart

import com.example.toramarket.domain.repository.*
import javax.inject.*

class ClearCartUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke() = repository.clearCart()
}