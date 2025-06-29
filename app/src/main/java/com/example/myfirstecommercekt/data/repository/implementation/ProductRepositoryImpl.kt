package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.local.dao.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import com.example.myfirstecommercekt.utils.data.*
import javax.inject.*

class ProductRepositoryImpl @Inject constructor(private val dao: ProductDao) : ProductRepository {
    override suspend fun getAll(): List<Product> = dao.getAll()
    override suspend fun fetchAndSaveRemoteProducts() {
        TODO("Not yet implemented")
    }

}