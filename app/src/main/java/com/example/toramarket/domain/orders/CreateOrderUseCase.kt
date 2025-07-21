package com.example.toramarket.domain.orders

import com.example.toramarket.domain.repository.*
import com.example.toramarket.utils.data.*
import javax.inject.*

class CreateOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(order: Order): Order {
        val new = order.toDto()
        return repository.createOrder(new).toDomain()
    }
}