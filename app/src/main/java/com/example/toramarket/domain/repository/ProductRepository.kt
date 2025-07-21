package com.example.toramarket.domain.repository

import com.example.toramarket.utils.data.*

interface ProductRepository {
    suspend fun getAll(refresh: Boolean): List<Product>
}