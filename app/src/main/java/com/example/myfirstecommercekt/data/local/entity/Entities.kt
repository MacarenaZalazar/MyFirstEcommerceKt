package com.example.myfirstecommercekt.data.local.entity

import androidx.room.*

@Entity(tableName = "products")
data class ProductEntity(@PrimaryKey(autoGenerate = true) val id: Int)

@Entity(tableName = "order")
data class OrderEntity(@PrimaryKey(autoGenerate = true) val id: Int, val userId: Int)

@Entity(tableName = "orderItem")
data class OrderItemEntity(@PrimaryKey(autoGenerate = true) val id: Int)

@Entity(tableName = "cartItem")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val quantity: Int,
    val price: Double
)