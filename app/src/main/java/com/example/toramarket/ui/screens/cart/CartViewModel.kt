package com.example.toramarket.ui.screens.cart

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.toramarket.domain.cart.*
import com.example.toramarket.ui.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel()
class CartViewModel @Inject constructor(
    getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val clearCartUseCase: ClearCartUseCase,
) : ViewModel() {

    var uiState by mutableStateOf<UIState<Boolean>>(UIState.Success(true))

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage

    private val _cartItems = getCartUseCase.invoke().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    val cartItems = _cartItems

    private val _subtotal = MutableStateFlow(0.0)
    val subtotal = _subtotal

    private val _count = MutableStateFlow<Int>(0)
    val count = _count

    init {
        updateTotals()

        viewModelScope.launch {
            _cartItems.collect { cart ->
                updateTotals()
            }
        }
    }

    fun addToCart(id: String) {
        viewModelScope.launch {
            try {
                addToCartUseCase.invoke(id)
                updateTotals()
                _snackbarMessage.emit("Producto agregado al carrito")

            } catch (e: Exception) {
                _snackbarMessage.emit("Error al agregar al carrito: ${e.message ?: "desconocido"}")

            }
        }
    }


    fun removeFromCart(id: String) {
        viewModelScope.launch {
            try {
                removeFromCartUseCase.invoke(id)
                updateTotals()
                _snackbarMessage.emit("Producto eliminado del carrito")
            } catch (e: Exception) {
                _snackbarMessage.emit("Error al eliminar del carrito: ${e.message ?: "desconocido"}")

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

}