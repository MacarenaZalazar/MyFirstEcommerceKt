package com.example.myfirstecommercekt.ui.screens.orders

import androidx.compose.runtime.*
import androidx.lifecycle.*
import coil.network.*
import com.example.myfirstecommercekt.domain.orders.*
import com.example.myfirstecommercekt.ui.*
import com.example.myfirstecommercekt.utils.data.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import okio.*
import javax.inject.*

@HiltViewModel()
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {
    private val user = ""

    var uiState by mutableStateOf<UIState<List<Order>>>(UIState.Loading)

    fun loadProducts(refresh: Boolean = false) {
        viewModelScope.launch {
            uiState = UIState.Loading
            try {
                val orders = getOrdersUseCase(user)
                uiState = UIState.Success(orders)
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