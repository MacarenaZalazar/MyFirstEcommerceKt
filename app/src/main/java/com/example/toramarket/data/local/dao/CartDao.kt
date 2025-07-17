package com.example.toramarket.data.local.dao

import androidx.room.*
import com.example.toramarket.data.local.entity.*
import kotlinx.coroutines.flow.*

@Dao
interface CartDao {
    @Transaction
    @Query("SELECT * FROM cartItem")
    fun getCartItems(): Flow<List<CartItemWithProduct>>

    @Transaction
    @Query("SELECT * FROM cartItem WHERE productId = :id LIMIT 1")
    suspend fun getCartItemById(id: String): CartItemWithProduct?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItemEntity)

    @Update
    suspend fun updateCartItem(item: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(item: CartItemEntity)

    @Query("DELETE FROM cartItem")
    suspend fun clearCart()
}