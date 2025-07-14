package com.example.toramarket.data.repository.interfaces

import com.example.toramarket.utils.data.*

interface ProductRepository {
    suspend fun getAll(refresh: Boolean): List<Product>
}