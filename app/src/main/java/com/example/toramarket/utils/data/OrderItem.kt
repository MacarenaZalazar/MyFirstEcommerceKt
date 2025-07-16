package com.example.toramarket.utils.data

import com.example.toramarket.data.remote.dto.*

data class OrderItem(
    val id: String,
    val productName: String,
    val quantity: Int,
    val price: Double
) {
    fun toDto() = NewOrderItemDto(
        productName = productName,
        quantity = quantity,
        price = price
    )
}
