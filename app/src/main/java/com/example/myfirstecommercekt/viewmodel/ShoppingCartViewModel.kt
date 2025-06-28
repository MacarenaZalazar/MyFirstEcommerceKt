package com.example.myfirstecommercekt.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myfirstecommercekt.model.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel()
class ShoppingCartViewModel @Inject constructor() : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartIems = _cartItems

    private val _subtotal = MutableStateFlow(0.0)
    val subtotal = _subtotal

    fun addToCart(){

    }
    fun deleteFromCart(){}

    fun updateTotals(){}

    fun updateQuantity(){

    }
}