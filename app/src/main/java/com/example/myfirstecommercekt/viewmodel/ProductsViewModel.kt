package com.example.myfirstecommercekt.viewmodel

import android.util.*
import androidx.lifecycle.*
import com.example.myfirstecommercekt.data.local.entity.*
import com.example.myfirstecommercekt.data.repository.implementation.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ProductsViewModel @Inject constructor(private val repo: ProductRepositoryImpl) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductEntity>>(emptyList())
    val products = _products

    private val _filteredProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    val filteredProducts = _filteredProducts

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success

    private val _filter = MutableStateFlow<String>("")
    val filter = _filter

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("PRODUCTOS", "INGRESANDO A LOADPRODUCTS")
                val local = repo.getAll()
                Log.d("PRODUCTOS", _products.value.toString())
                if (local.isEmpty()) {
                    val remote = repo.getAllRemote()
                    if (remote.isEmpty()) _success.value = false
                    _products.value = remote
                    _filteredProducts.value = remote
                } else {
                    _products.value = local
                    _filteredProducts.value = local
                }
                Log.d("PRODUCTOS", _products.value.toString())


            } catch (e: Exception) {
                Log.d("PRODUCTS", e.toString())
                _success.value = false
            }
            _isLoading.value = false
        }
    }

    fun filterProducts(value: String) {
        _filter.value = value
        if (value.isBlank()) _filteredProducts.value = _products.value;
        else {
            _filteredProducts.value =
                _products.value.filter { it -> it.name.contains(value.trim(), ignoreCase = true) }
        }

    }
}
