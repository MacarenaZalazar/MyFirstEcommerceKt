package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.local.dao.*
import com.example.myfirstecommercekt.data.remote.api.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import com.example.myfirstecommercekt.utils.data.*
import javax.inject.*

class ProductRepositoryImpl @Inject constructor(
    private val local: ProductDao,
    private val remote: ProductsService
) : ProductRepository {
    override suspend fun getAll(refresh: Boolean): List<Product> {
        return if (refresh) {
            val res = remote.getProducts()
            local.clearProducts()
            local.insertAll(res.map { it.toEntity() })
            res.map { it.toDomain() }
        } else {
            val localProducts = local.getAll()
            if (localProducts.isNotEmpty()) {
                localProducts.map { it.toDomain() }
            } else {
                val remote = remote.getProducts()
                local.insertAll(remote.map { it.toEntity() })
                remote.map { it.toDomain() }
            }
        }
    }
}