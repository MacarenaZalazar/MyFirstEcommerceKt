package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.remote.api.*
import com.example.myfirstecommercekt.data.remote.dto.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import javax.inject.*

class OrderRepositoryImpl @Inject constructor(private val service: OrderService) : OrderRepository {
    override suspend fun getOrdersByUser(id: String): List<OrderDto> =
        service.getOrdersByUser(id)

    override suspend fun createOrder(request: NewOrderDto): OrderResponseDto =
        service.createOrder(request)
}