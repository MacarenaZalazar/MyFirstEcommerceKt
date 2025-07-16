package com.example.toramarket.data.repository.implementation

import com.example.toramarket.data.remote.api.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.data.repository.interfaces.*
import javax.inject.*

class OrderRepositoryImpl @Inject constructor(private val service: OrderService) : OrderRepository {
    override suspend fun getOrdersByUser(id: String): List<OrderDto> =
        service.getOrdersByUser(id)

    override suspend fun createOrder(request: NewOrderDto): OrderDto =
        service.createOrder(request)
}