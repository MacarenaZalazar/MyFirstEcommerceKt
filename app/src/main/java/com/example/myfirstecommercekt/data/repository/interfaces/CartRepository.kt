package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.data.local.entity.*
import kotlinx.coroutines.flow.*

interface CartRepository {
    fun getCartItems(): Flow<List<CartItemWithProduct>>
    suspend fun getCartItemById(id: String): CartItemWithProduct?
    suspend fun insertCartItem(item: CartItemEntity)
    suspend fun updateCartItem(item: CartItemEntity)
    suspend fun deleteCartItem(item: CartItemEntity)
    suspend fun clearCart()
}