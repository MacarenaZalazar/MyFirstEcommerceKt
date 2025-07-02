package com.example.myfirstecommercekt.data.remote.dto

import java.util.*

data class OrderDto(val userId: Int, val created: Date)
data class OrderItemDto(val id: Int, val productName: Int, val quantity: Int, val price: Double)
data class NewOrderItemDto(val productName: String, val quantity: Int, val price: Double)
data class NewOrderDto(val userId: Int, val items: List<NewOrderItemDto>)

data class OrderResponseDto(val message: String)