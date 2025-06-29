package com.example.myfirstecommercekt.data.local.dao

import androidx.room.*
import com.example.myfirstecommercekt.data.local.entity.*

@Dao
interface OrderDao {
    suspend fun getOrderByUser(id: Int): OrderEntity
    suspend fun getAllOrdersByUser(id: Int): List<OrderEntity>
    suspend fun createOrder(item: OrderEntity)
    suspend fun updateOrder(order: OrderEntity)
}