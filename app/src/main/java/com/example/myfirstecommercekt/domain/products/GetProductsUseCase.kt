package com.example.myfirstecommercekt.domain.products

import com.example.myfirstecommercekt.data.repository.interfaces.*
import javax.inject.*

class GetProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(refresh: Boolean) = repository.getAll(refresh)
}