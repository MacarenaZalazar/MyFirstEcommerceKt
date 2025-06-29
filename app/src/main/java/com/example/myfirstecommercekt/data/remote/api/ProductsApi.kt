package com.example.myfirstecommercekt.data.remote.api

import com.example.myfirstecommercekt.utils.data.*

interface ProductsApi {
    suspend fun getProducts(): List<ProductDto>
}