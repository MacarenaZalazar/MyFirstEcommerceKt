package com.example.toramarket.domain.orders

import com.example.toramarket.data.repository.interfaces.*
import com.example.toramarket.utils.data.*
import javax.inject.*

class GetOrdersUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(userId: String): List<Order> {
        val orders = repository.getOrdersByUser(userId)
        return orders.map { it.toDomain() }
    }
}