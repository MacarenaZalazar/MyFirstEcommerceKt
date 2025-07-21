package com.example.toramarket.data.repository.implementation

import com.example.toramarket.data.local.dao.*
import com.example.toramarket.data.remote.api.*
import com.example.toramarket.domain.repository.*
import com.example.toramarket.utils.data.*
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