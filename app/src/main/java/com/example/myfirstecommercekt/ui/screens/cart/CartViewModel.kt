package com.example.myfirstecommercekt.ui.screens.cart

import androidx.lifecycle.*
import com.example.myfirstecommercekt.domain.cart.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel()
class CartViewModel @Inject constructor(
    getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {
    private val _cartItems = getCartUseCase.invoke().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val cartItems = _cartItems

    private val _subtotal = MutableStateFlow(0.0)
    val subtotal = _subtotal

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success

    private val _count = MutableStateFlow<Int>(0)
    val count = _count

    fun addToCart(id: String) {
        viewModelScope.launch {
            try {
                addToCartUseCase.invoke(id)
            } catch (e: Exception) {
                TODO()
            } finally {
                updateTotals()
            }

        }
    }


    fun removeFromCart(id: String) {
        viewModelScope.launch {
            try {
                removeFromCartUseCase.invoke(id)
            } catch (e: Exception) {
                TODO()
            } finally {
                updateTotals()
            }
        }

    }

    fun clearCart() {
        viewModelScope.launch {
            clearCartUseCase.invoke()
        }

    }

    fun updateTotals() {
        _subtotal.value = _cartItems.value.sumOf { it.product.price * it.cartItem.quantity }
        _count.value = _cartItems.value.sumOf { it.cartItem.quantity }
    }

    fun buy() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                TODO()
            } catch (e: Exception) {
                TODO()
            } finally {
                TODO()
            }
        }
    }
}