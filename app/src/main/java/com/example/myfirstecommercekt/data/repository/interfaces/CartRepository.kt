package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.data.local.entity.*

interface CartRepository {
    suspend fun getCartItems(): List<CartItemEntity>
    suspend fun getCartItemById(id: Int): CartItemEntity
    suspend fun insertCartItem(item: CartItemEntity)
    suspend fun updateCartItem(item: CartItemEntity)
    suspend fun deleteCartItem(item: CartItemEntity)
    suspend fun clearCart()
}