package com.example.myfirstecommercekt.data.remote.api

import com.example.myfirstecommercekt.data.remote.dto.*
import retrofit2.*
import retrofit2.http.*

interface ProductsApi {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductDto>>
}