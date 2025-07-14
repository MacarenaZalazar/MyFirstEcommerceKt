package com.example.toramarket.data.remote.dto

import com.example.toramarket.utils.data.*
import java.util.*

data class OrderDto(
    val id: String,
    val userId: String,
    val created: Date,
    val items: List<OrderItemDto>
) {
    fun toDomain() = Order(
        id = id,
        items = items.map { it.toDomain() },
        created = created
    )
}

data class OrderItemDto(
    val id: String,
    val productName: String,
    val quantity: Int,
    val price: Double
) {
    fun toDomain() = OrderItem(
        id = id,
        productName = productName,
        quantity = quantity,
        price = price
    )
}

data class NewOrderItemDto(val productName: String, val quantity: Int, val price: Double)
data class NewOrderDto(val userId: String, val items: List<NewOrderItemDto>)

data class OrderResponseDto(val message: String)