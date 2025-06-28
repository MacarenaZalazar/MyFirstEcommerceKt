package com.example.myfirstecommercekt.data.repository

import com.example.myfirstecommercekt.data.local.entity.*

interface ProductRepository {
    suspend fun getAll(): List<ProductEntity>
    suspend fun insert(product: ProductEntity)
    suspend fun delete(product: ProductEntity)
    suspend fun fetchAndSaveRemoteProducts()

}