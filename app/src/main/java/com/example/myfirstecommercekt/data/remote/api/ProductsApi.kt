package com.example.myfirstecommercekt.data.remote.api

import com.example.myfirstecommercekt.utils.data.*
import retrofit2.http.*

interface ProductsApi {
    @GET("/products")
    suspend fun getProducts(): List<ProductDto>
}