package com.example.myfirstecommercekt.viewmodel

import androidx.lifecycle.*
import com.example.myfirstecommercekt.data.local.entity.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel()
class CartViewModel @Inject constructor(
    private val cartRepo: CartRepository

) : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItemWithProduct>>(emptyList())
    val cartItems = _cartItems

    private val _subtotal = MutableStateFlow(0.0)
    val subtotal = _subtotal

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success

    private val _count = MutableStateFlow<Int>(0)
    val count = _count


    init {
        loadCart()
    }

    fun loadCart() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val items = cartRepo.getCartItems()
                if (items.isNotEmpty()) _cartItems.value = items
            } catch (e: Exception) {
                println(e)
                _success.value = false
            } finally {
                updateTotals()
                _isLoading.value = false
            }
        }
    }

    fun addToCart(product: ProductEntity) {
        viewModelScope.launch {
            try {
                val existing = _cartItems.value.firstOrNull { it.cartItem.productId == product.id }
                if (existing != null) {
                    val updated = existing.cartItem.copy(quantity = existing.cartItem.quantity + 1)
                    cartRepo.updateCartItem(updated)
                } else {
                    val newItem =
                        CartItemEntity(productId = product.id, quantity = 1, price = product.price)
                    cartRepo.insertCartItem(newItem)
                }
                _cartItems.value = cartRepo.getCartItems()
            } catch (e: Exception) {
                println(e)
            } finally {
                updateTotals()

            }

        }
    }


    fun removeFromCart(product: ProductEntity) {
        viewModelScope.launch {
            try {
                val existing = _cartItems.value.firstOrNull { it.cartItem.productId == product.id }
                existing?.let {
                    if (it.cartItem.quantity > 1) {
                        val updated =
                            existing.cartItem.copy(quantity = existing.cartItem.quantity - 1)
                        cartRepo.updateCartItem(updated)
                    } else {
                        cartRepo.deleteCartItem(existing.cartItem)
                    }
                    _cartItems.value = cartRepo.getCartItems()
                }

            } catch (e: Exception) {
                println(e)
            } finally {

                updateTotals()
            }
        }

    }

    fun clearCart() {
        viewModelScope.launch {
            cartRepo.clearCart()
            _cartItems.value = emptyList()
        }

    }

    fun updateTotals() {
        _subtotal.value = _cartItems.value.sumOf { it.product.price * it.cartItem.quantity }
        _count.value = _cartItems.value.sumOf { it.cartItem.quantity }
    }
}