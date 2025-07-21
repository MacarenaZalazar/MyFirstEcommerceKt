package com.example.toramarket.ui.screens.products

import androidx.compose.runtime.*
import androidx.lifecycle.*
import coil.network.*
import com.example.toramarket.domain.cart.*
import com.example.toramarket.domain.products.*
import com.example.toramarket.ui.*
import com.example.toramarket.utils.data.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okio.*
import javax.inject.*

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase
) :
    ViewModel() {

    var uiState by mutableStateOf<UIState<List<Product>>>(UIState.Loading)
    var filter by mutableStateOf("")
    var selectedCategory by mutableStateOf<String?>(null)

    private val _loadingPerItem = mutableStateMapOf<String, Boolean>()
    fun isItemLoading(productId: String) = _loadingPerItem[productId] == true

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage

    fun onFilterChange(newFilter: String) {
        filter = newFilter
    }

    val filteredProducts: List<Product>
        get() = (uiState as? UIState.Success<List<Product>>)?.data
            ?.filter { product ->
                (selectedCategory == null || product.category == selectedCategory) &&
                        (filter.isBlank() || product.name.contains(filter, ignoreCase = true))
            } ?: emptyList()

    fun loadProducts(refresh: Boolean = false) {
        viewModelScope.launch {
            uiState = UIState.Loading
            try {
                val products = getProductsUseCase.invoke(refresh)
                uiState = UIState.Success(products)
            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a internet")
            } catch (e: HttpException) {
                uiState = UIState.Error("Error del servidor")
            } catch (e: Exception) {
                uiState = UIState.Error("Ocurrió un error inesperado")
            }
        }

    }

    fun addToCart(productId: String) {
        if (_loadingPerItem[productId] == true) return
        _loadingPerItem[productId] = true
        viewModelScope.launch {
            try {
                addToCartUseCase.invoke(productId)
                _snackbarMessage.emit("Producto agregado al carrito")
            } catch (e: Exception) {
                _snackbarMessage.emit("Error al agregar al carrito")
            } finally {
                _loadingPerItem[productId] = false
            }

        }
    }
}