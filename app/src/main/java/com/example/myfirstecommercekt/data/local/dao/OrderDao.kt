package com.example.myfirstecommercekt.data.local.dao

import androidx.room.*
import com.example.myfirstecommercekt.data.local.entity.*

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders order by id desc LIMIT 1 ")
    suspend fun getOrderByUser(): OrderEntity

    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createOrder(item: OrderEntity)

    @Update
    suspend fun updateOrder(order: OrderEntity)
}