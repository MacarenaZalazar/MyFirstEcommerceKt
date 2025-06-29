package com.example.myfirstecommercekt.data.local.dao

import androidx.room.*
import com.example.myfirstecommercekt.data.local.entity.*

@Dao
interface CartDao {
    suspend fun getCartItems(): List<CartItemEntity>
    suspend fun getCartItemById(id: Int): CartItemEntity
    suspend fun insertCartItem(item: CartItemEntity)
    suspend fun updateCartItem(item: CartItemEntity)
    suspend fun deleteCartItem(item: CartItemEntity)
    suspend fun clearCart()
}