package com.example.myfirstecommercekt.data.remote.api

import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.http.*

interface ProductsService {
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
}