package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.utils.data.*

interface ProductRepository {
    suspend fun getAll(refresh: Boolean): List<Product>
}