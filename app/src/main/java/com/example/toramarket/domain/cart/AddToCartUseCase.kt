package com.example.toramarket.domain.cart

import android.util.*
import com.example.toramarket.data.local.entity.*
import com.example.toramarket.data.repository.interfaces.*
import javax.inject.*

class AddToCartUseCase @Inject constructor(private val repository: CartRepository) {
    suspend operator fun invoke(productId: String) {
        val existing = repository.getCartItemById(productId)
        Log.d("AddToCartUseCase", "Existing item: $existing")
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