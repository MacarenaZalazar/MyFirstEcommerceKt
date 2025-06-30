package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.local.dao.*
import com.example.myfirstecommercekt.data.local.entity.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import javax.inject.*

class ProductRepositoryImpl @Inject constructor(private val dao: ProductDao) : ProductRepository {
    override suspend fun getAll(): List<ProductEntity> = dao.getAll()
    override suspend fun fetchAndSaveRemoteProducts(): List<ProductEntity> {
        return emptyList()
    }

}