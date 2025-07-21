package com.example.toramarket.domain.cart

import com.example.toramarket.domain.repository.*
import javax.inject.*

class GetCartUseCase @Inject constructor(private val repository: CartRepository) {
    operator fun invoke() = repository.getCartItems()
}