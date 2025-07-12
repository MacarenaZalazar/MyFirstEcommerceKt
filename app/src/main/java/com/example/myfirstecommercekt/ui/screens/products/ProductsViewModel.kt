package com.example.myfirstecommercekt.ui.screens.products

import androidx.compose.runtime.*
import androidx.lifecycle.*
import coil.network.*
import com.example.myfirstecommercekt.domain.products.*
import com.example.myfirstecommercekt.ui.*
import com.example.myfirstecommercekt.utils.data.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import okio.*
import javax.inject.*

@HiltViewModel
class ProductsViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) :
    ViewModel() {

    var uiState by mutableStateOf<UIState<List<Product>>>(UIState.Loading)

    fun loadProducts(refresh: Boolean = false) {
        viewModelScope.launch {
            uiState = UIState.Loading
            try {
                val products = getProductsUseCase(refresh)
                uiState = UIState.Success(products)
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