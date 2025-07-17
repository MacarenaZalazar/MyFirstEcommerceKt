package com.example.toramarket.utils.data

import com.example.toramarket.data.remote.dto.*
import java.util.*

data class Order(
    val id: String,
    val userId: String,
    val items: List<OrderItem>,
    val created: Date,
    val total: Double
) {
    fun toDto() = NewOrderDto(
        user = userId,
        items = items.map { it.toDto() },
        total = total
    )
}