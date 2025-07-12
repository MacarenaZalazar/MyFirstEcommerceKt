package com.example.myfirstecommercekt.domain.orders

import com.example.myfirstecommercekt.data.repository.interfaces.*
import com.example.myfirstecommercekt.utils.data.*
import javax.inject.*

class GetOrdersUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(userId: String): List<Order> {
        val orders = repository.getOrdersByUser(userId)
        return orders.map { it.toDomain() }
    }
}