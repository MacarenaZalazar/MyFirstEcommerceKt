package com.example.toramarket.domain.orders

import android.util.*
import com.example.toramarket.data.repository.interfaces.*
import com.example.toramarket.utils.data.*
import javax.inject.*

class GetOrdersUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(userId: String): List<Order> {
        val orders = repository.getOrdersByUser(userId)
        Log.d("GetOrdersUseCase", "Fetched orders for user $userId: $orders")
        return orders.map { it.toDomain() }
    }
}