package com.example.toramarket.data.repository.interfaces

import com.example.toramarket.data.remote.dto.*

interface OrderRepository {
    suspend fun getOrdersByUser(id: String): List<OrderDto>
    suspend fun createOrder(request: NewOrderDto): OrderDto

}