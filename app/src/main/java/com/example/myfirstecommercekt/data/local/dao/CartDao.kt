package com.example.myfirstecommercekt.data.local.dao

import androidx.room.*
import com.example.myfirstecommercekt.data.local.entity.*

@Dao
interface CartDao {
    @Query("SELECT * FROM cartItem")
    suspend fun getCartItems(): List<CartItemWithProduct>

    @Query("SELECT * FROM cartItem WHERE id = :id LIMIT 1")
    suspend fun getCartItemById(id: Int): CartItemWithProduct

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItemEntity)

    @Update
    suspend fun updateCartItem(item: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(item: CartItemEntity)

    @Query("DELETE FROM cartItem")
    suspend fun clearCart()
}