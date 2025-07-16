package com.example.toramarket.domain.orders

import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.data.repository.interfaces.*
import com.example.toramarket.utils.data.*
import javax.inject.*

class CreateOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(userId: String, items: List<OrderItem>): OrderDto {
        val order = NewOrderDto(userId = userId, items = items.map { it.toDto() })
        return repository.createOrder(order)
    }
}