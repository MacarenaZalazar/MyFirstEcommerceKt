package com.example.toramarket.data.remote.dto

import com.example.toramarket.data.local.entity.*
import com.example.toramarket.utils.data.*
import com.google.gson.annotations.*

data class ProductDto(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String
) {
    fun toDomain() = Product(id, name, description, price, category, imageUrl)
    fun toEntity() = ProductEntity(
        id = id,
        name = name,
        description = description,
        price = price,
        category = category,
        imageUrl = imageUrl
    )
}