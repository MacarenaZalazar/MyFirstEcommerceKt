package com.example.myfirstecommercekt.data.local

import androidx.room.*
import com.example.myfirstecommercekt.data.local.dao.*
import com.example.myfirstecommercekt.data.local.entity.*

@Database(
    entities = [
        ProductEntity::class,
        CartItemEntity::class,
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao

}