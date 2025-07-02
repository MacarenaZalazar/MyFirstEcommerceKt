package com.example.myfirstecommercekt.data.remote.api

import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.*
import retrofit2.http.*

interface OrderService {
    @GET("orders/{id}")
    suspend fun getOrdersByUser(@Path("id") id: Int): Response<List<OrderDto>>

    @POST("order")
    suspend fun createOrder(@Body request: NewOrderDto): Response<OrderResponseDto>
}