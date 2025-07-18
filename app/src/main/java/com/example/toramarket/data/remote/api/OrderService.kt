package com.example.toramarket.data.remote.api

import com.example.toramarket.data.remote.dto.*
import retrofit2.http.*

interface OrderService {
    @GET("orders/{id}")
    suspend fun getOrdersByUser(@Path("id") id: String): List<OrderDto>

    @POST("orders")
    suspend fun createOrder(@Body request: NewOrderDto): OrderDto
}