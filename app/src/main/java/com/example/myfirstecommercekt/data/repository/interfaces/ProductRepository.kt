package com.example.myfirstecommercekt.data.repository.interfaces

import com.example.myfirstecommercekt.data.local.entity.*

interface ProductRepository {
    suspend fun getAll(): List<ProductEntity>
    suspend fun fetchAndSaveRemoteProducts(): List<ProductEntity>

}