package com.example.myfirstecommercekt.data.remote.api

import com.example.myfirstecommercekt.model.*

interface ProductsApi {
    suspend fun getProducts(): List<ProductDto>
}