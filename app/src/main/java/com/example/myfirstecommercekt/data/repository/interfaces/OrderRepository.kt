package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.data.remote.dto.*

interface OrderRepository {
    suspend fun getOrdersByUser(id: String): List<OrderDto>
    suspend fun createOrder(request: NewOrderDto): OrderResponseDto

}