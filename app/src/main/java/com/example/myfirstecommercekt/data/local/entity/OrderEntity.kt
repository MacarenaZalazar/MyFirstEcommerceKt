package com.example.myfirstecommercekt.data.local.entity

import androidx.room.*


@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int, val userId: Int, val active: Boolean = true,
    val total: Double
)

