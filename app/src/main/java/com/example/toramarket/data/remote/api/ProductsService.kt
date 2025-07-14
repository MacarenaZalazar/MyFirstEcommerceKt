package com.example.toramarket.data.remote.api

import com.example.toramarket.data.remote.dto.*
import retrofit2.http.*

interface ProductsService {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
}