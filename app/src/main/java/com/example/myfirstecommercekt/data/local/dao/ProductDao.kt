package com.example.myfirstecommercekt.data.local.dao

import androidx.room.*
import com.example.myfirstecommercekt.data.local.entity.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    suspend fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clearProducts()
}