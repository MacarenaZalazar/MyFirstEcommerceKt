package com.example.toramarket.domain.cart

import com.example.toramarket.domain.repository.*
import javax.inject.*

class RemoveFromCartUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke(productId: String) {
        val existing = repository.getCartItemById(productId)
        existing?.let {
            if (it.cartItem.quantity > 1) {
                val updated =
                    existing.cartItem.copy(quantity = existing.cartItem.quantity - 1)
                repository.updateCartItem(updated)
            } else {
                repository.deleteCartItem(existing.cartItem)

            }
        }
    }
}