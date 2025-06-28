package com.example.myfirstecommercekt.data.local.entity

import androidx.room.*

@Entity(tableName = "products")
data class ProductEntity(@PrimaryKey val id: String)

@Entity(tableName = "order")
data class OrderEntity(@PrimaryKey val id: String)

@Entity(tableName = "orderItem")
data class OrderItemEntity(@PrimaryKey val id: String)

@Entity(tableName = "cartItem")
data class CartItemEntity(@PrimaryKey val id: String)