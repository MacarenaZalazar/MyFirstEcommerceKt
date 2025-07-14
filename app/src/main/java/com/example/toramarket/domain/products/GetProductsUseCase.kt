package com.example.toramarket.domain.products

import com.example.toramarket.data.repository.interfaces.*
import javax.inject.*

class GetProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(refresh: Boolean) = repository.getAll(refresh)
}