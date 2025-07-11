package com.example.myfirstecommercekt.domain.cart

import com.example.myfirstecommercekt.data.repository.interfaces.*
import javax.inject.*

class GetCartUseCase @Inject constructor(private val repository: CartRepository) {
    operator fun invoke() = repository.getCartItems()
}