package com.example.myfirstecommercekt.ui.screens.checkout

import androidx.lifecycle.*
import com.example.myfirstecommercekt.data.*
import com.example.myfirstecommercekt.data.repository.implementation.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepo: CartRepository,
    private val orderRepo: OrderRepositoryImpl,
    private val userData: UserDataStore
) : ViewModel() {

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

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success
    fun onChange(cardNumber: String, cardName: String, expiration: String, ccv: String) {

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
                _isLoading.value = true
                delay(4000)
//                val items = cartRepo.getCartItems()
//                val user = userData.userId.first()
//                if (items.isNotEmpty() && user != null) {
//                    val order = NewOrderDto(
//                        userId = user.toInt(), items = items.map {
//                            NewOrderItemDto(
//                                productName = it.product.name,
//                                quantity = it.cartItem.quantity,
//                                price = it.product.price
//                            )
//                        })
//                    val res = orderRepo.createOrder(order)
//                    if (!res.isSuccessful) _success.value = false
//                    else {
                cartRepo.clearCart()
                _dialogMessage.value = "Tu compra ha sido realizada con éxito"
                _showDialog.value = true
//                    }
//                }
            } catch (e: Exception) {
                _success.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}