package com.example.myfirstecommercekt.data.repository.implementation

import com.example.myfirstecommercekt.data.local.dao.*
import com.example.myfirstecommercekt.data.local.entity.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import kotlinx.coroutines.flow.*
import javax.inject.*

class CartRepositoryImpl @Inject constructor(private val dao: CartDao) : CartRepository {
    override fun getCartItems(): Flow<List<CartItemWithProduct>> = dao.getCartItems()
    override suspend fun getCartItemById(id: String): CartItemWithProduct? = dao.getCartItemById(id)
    override suspend fun insertCartItem(item: CartItemEntity) = dao.insertCartItem(item)
    override suspend fun updateCartItem(item: CartItemEntity) = dao.updateCartItem(item)
    override suspend fun deleteCartItem(item: CartItemEntity) = dao.deleteCartItem(item)
    override suspend fun clearCart() = dao.clearCart()
}