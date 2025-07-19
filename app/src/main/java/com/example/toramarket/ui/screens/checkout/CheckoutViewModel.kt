package com.example.toramarket.ui.screens.checkout

import android.util.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import coil.network.*
import com.example.toramarket.domain.cart.*
import com.example.toramarket.domain.orders.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.ui.*
import com.example.toramarket.utils.data.*
import com.example.toramarket.utils.helpers.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okio.*
import java.util.*
import javax.inject.*

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val clearCartUseCase: ClearCartUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getCartUseCase: GetCartUseCase
) : ViewModel() {

    var uiState by mutableStateOf<UIState<Order>>(UIState.Loading)
    private val _order = MutableStateFlow<Order?>(null)

    private val _number = MutableStateFlow<String>("")
    val number: MutableStateFlow<String> = _number

    private val _name = MutableStateFlow<String>("")
    val name: MutableStateFlow<String> = _name

    private val _expiration = MutableStateFlow<String>("")
    val expiration: MutableStateFlow<String> = _expiration

    private val _ccv = MutableStateFlow<String>("")
    val ccv: MutableStateFlow<String> = _ccv

    private val _isValid = MutableStateFlow<Boolean>(true)
    val isValid: MutableStateFlow<Boolean> = _isValid

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success

    fun getOrder() {
        viewModelScope.launch {
            try {
                val userId = getUserIdUseCase.invoke()
                if (userId != null) {
                    Log.d("USER_ID", "User ID: $userId")
                    val cart = getCartUseCase.invoke().first()
                    if (cart.isNotEmpty()) {
                        val order = Order(
                            id = "",
                            userId = userId,
                            items = cart.map { it.toOrderItem() },
                            created = Date(),
                            total = cart.sumOf { it.product.price * it.cartItem.quantity })
                        _order.value = order
                        uiState = UIState.Success(order)
                    } else {
                        uiState = UIState.Error("El carrito está vacío")
                    }
                } else {
                    uiState = UIState.Error("Usuario no encontrado")
                }
            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a internet")
            } catch (e: HttpException) {
                uiState = UIState.Error("Error del servidor: ${e.message}")
            } catch (e: Exception) {
                uiState = UIState.Error("Ocurrió un error inesperado")
            }
        }
    }

    fun onChange(cardNumber: String, cardName: String, expiration: String, ccv: String) {
        _number.value = cardNumber
        _name.value = cardName
        _expiration.value = expiration
        _ccv.value = ccv
        _isValid.value = isValidCard(cardNumber)

    }

    private val _showDialog = MutableStateFlow<Boolean>(false)
    val showDialog = _showDialog


    private val _dialogMessage = MutableStateFlow<String>("")
    val dialogMessage = _dialogMessage
    fun closeDialog() {
        _showDialog.value = false
    }

    fun pay() {
        viewModelScope.launch {
            try {
                uiState = UIState.Loading
                if (_order.value != null) {
                    Log.d("ORDER", "Order: ${_order.value}")
                    createOrderUseCase.invoke(_order.value!!)
                    clearCartUseCase.invoke()
                    _dialogMessage.value = "Tu compra ha sido realizada con éxito"
                    _showDialog.value = true
                    uiState = UIState.Success(_order.value!!)
                }
            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a internet")
            } catch (e: HttpException) {
                uiState = UIState.Error("Error del servidor: ${e.message}")
            } catch (e: Exception) {
                uiState = UIState.Error("Ocurrió un error inesperado")
            }

        }
    }
}