package com.example.myfirstecommercekt.data.local.dao

import androidx.room.*
import com.example.myfirstecommercekt.utils.data.*

@Dao
interface ProductDao {
    suspend fun getAll(): List<Product>
    suspend fun insert(product: Product)
    suspend fun delete(product: Product)
}