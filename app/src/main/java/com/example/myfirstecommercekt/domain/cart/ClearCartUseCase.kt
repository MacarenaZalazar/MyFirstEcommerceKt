package com.example.myfirstecommercekt.domain.cart

import com.example.myfirstecommercekt.data.repository.interfaces.*
import javax.inject.*

class ClearCartUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke() = repository.clearCart()
}