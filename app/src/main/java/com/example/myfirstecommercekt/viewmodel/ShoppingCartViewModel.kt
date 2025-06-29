package com.example.myfirstecommercekt.viewmodel

import androidx.lifecycle.*
import com.example.myfirstecommercekt.data.local.entity.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import com.example.myfirstecommercekt.utils.data.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel()
class ShoppingCartViewModel @Inject constructor(
    private val cartRepo: CartRepository

) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItemEntity>>(emptyList())
    val cartItems = _cartItems

    private val _subtotal = MutableStateFlow(0.0)
    val subtotal = _subtotal

    init {
        loadCart()
    }


    fun loadCart() {
        viewModelScope.launch {
            _cartItems.value = cartRepo.getCartItems()
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                val existing = _cartItems.value.firstOrNull { it.productId == product.id }
                if (existing != null) {
                    val updated = existing.copy(quantity = existing.quantity + 1)
                    cartRepo.updateCartItem(updated)
                } else {

                    val newItem =
                        CartItemEntity(productId = product.id, quantity = 1, price = product.price)
                    cartRepo.insertCartItem(newItem)
                }

            } catch (e: Exception) {
            }

            _cartItems.update { currentList ->
                val idx = currentList.indexOfFirst { it.productId == product.id }
                if (idx >= 0) {
                    currentList.map { item ->
                        if (item.productId == product.id) item.copy(quantity = item.quantity + 1)
                        else item
                    }
                } else {
                    currentList + CartItemEntity(
                        productId = product.id, quantity = 1, price = product.price
                    )
                }
            }
            updateTotals()
        }
    }


    fun deleteFromCart(product: Product) {
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
            }
            updateTotals()
        }

    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepo.clearCart()
            _cartItems.value = emptyList()
        }

    }

    fun updateTotals() {
        _subtotal.value = _cartItems.value.sumOf { it.price * it.quantity }
    }
}