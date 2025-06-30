package com.example.myfirstecommercekt.data.local.entity

import androidx.room.*

@Entity(tableName = "cartItem")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val quantity: Int,
    val price: Double
)