package com.example.toramarket.domain.cart

import com.example.toramarket.data.local.entity.*
import com.example.toramarket.domain.repository.*
import javax.inject.*

class AddToCartUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke(productId: String) {
        val existing = repository.getCartItemById(productId)
        if (existing != null) {
            val updated = existing.cartItem.copy(quantity = existing.cartItem.quantity + 1)
            repository.updateCartItem(updated)
        } else {
            val newItem =
                CartItemEntity(productId = productId, quantity = 1)
            repository.insertCartItem(newItem)
        }

    }
}