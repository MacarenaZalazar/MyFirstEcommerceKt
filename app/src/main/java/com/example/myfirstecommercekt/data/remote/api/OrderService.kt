package com.example.myfirstecommercekt.data.remote.api

import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.http.*

interface OrderService {
    @GET("orders/{id}")
    suspend fun getOrdersByUser(@Path("id") id: String): List<OrderDto>

    @POST("order")
    suspend fun createOrder(@Body request: NewOrderDto): OrderResponseDto
}