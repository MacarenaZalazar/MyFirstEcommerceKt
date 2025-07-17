package com.example.toramarket.domain.orders

import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.data.repository.interfaces.*
import com.example.toramarket.utils.data.*
import javax.inject.*

class CreateOrderUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend operator fun invoke(order: Order): OrderDto {
        val new = order.toDto()
        return repository.createOrder(new)
    }
}