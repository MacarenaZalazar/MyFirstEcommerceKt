package com.example.toramarket.data.local.entity

import androidx.room.*
import com.example.toramarket.utils.data.*

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String

) {
    fun toDomain() = Product(id, name, description, price, category, imageUrl)
}
