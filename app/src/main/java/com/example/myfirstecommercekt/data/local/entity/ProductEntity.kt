package com.example.myfirstecommercekt.data.local.entity

import androidx.room.*

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val img: String
)