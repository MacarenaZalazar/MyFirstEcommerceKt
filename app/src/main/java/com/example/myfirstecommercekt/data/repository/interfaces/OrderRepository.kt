package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.*

interface OrderRepository {
    suspend fun getOrdersByUser(id: Int): Response<List<OrderDto>>
    suspend fun createOrder(request: NewOrderDto): Response<OrderResponseDto>

}