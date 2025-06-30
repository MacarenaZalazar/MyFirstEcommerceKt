package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.local.dao.*
import com.example.myfirstecommercekt.data.local.entity.*
import com.example.myfirstecommercekt.data.remote.api.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import javax.inject.*

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: ProductsApi
) : ProductRepository {
    override suspend fun getAll(): List<ProductEntity> = dao.getAll()
    override suspend fun getAllRemote(): List<ProductEntity> {
        val res = api.getProducts()
        if (res.body() != null && res.body()!!.isNotEmpty()) {
            val products = res.body()!!
            val entities = products.map { it ->
                ProductEntity(
                    name = it.name,
                    description = it.description,
                    price = it.price,
                    category = it.category,
                    imageUrl = it.imageUrl
                )
            }
            dao.insertAll(entities)
        }
        return dao.getAll()
    }

}