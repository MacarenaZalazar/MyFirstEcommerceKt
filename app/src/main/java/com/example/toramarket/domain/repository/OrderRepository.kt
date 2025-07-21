package com.example.toramarket.domain.repository

import com.example.toramarket.data.remote.dto.*

interface OrderRepository {
    suspend fun getOrdersByUser(id: String): List<OrderDto>
    suspend fun createOrder(request: NewOrderDto): OrderDto

}