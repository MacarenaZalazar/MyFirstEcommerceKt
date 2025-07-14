package com.example.toramarket.data.local.entity

import androidx.room.*

@Entity(tableName = "cartItem")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val quantity: Int,
)