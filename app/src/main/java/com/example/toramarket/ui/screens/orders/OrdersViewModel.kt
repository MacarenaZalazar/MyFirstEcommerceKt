package com.example.toramarket.ui.screens.orders

import android.util.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import coil.network.*
import com.example.toramarket.domain.orders.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.ui.*
import com.example.toramarket.utils.data.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okio.*
import javax.inject.*

@HiltViewModel()
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : ViewModel() {
    private val _user = MutableStateFlow("")

    var uiState by mutableStateOf<UIState<List<Order>>>(UIState.Loading)

    fun loadOrders(refresh: Boolean = false) {
        viewModelScope.launch {
            uiState = UIState.Loading
            try {
                _user.value = getUserIdUseCase.invoke() ?: ""
                Log.d("OrdersViewModel", "User ID: ${_user.value}")
                if (_user.value.isNotEmpty()) {
                    val orders = getOrdersUseCase.invoke(_user.value)
                    Log.d("OrdersViewModel", "Orders: $orders")
                    uiState = UIState.Success(orders)
                } else {
                    uiState = UIState.Error("Usuario no encontrado")
                }
            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a internet")
            } catch (e: HttpException) {
                uiState = UIState.Error("Error del servidor: ${e.message}")
            } catch (e: Exception) {
                uiState = UIState.Error("Ocurrió un error inesperado: ${e.message}")
            }
        }

    }
}