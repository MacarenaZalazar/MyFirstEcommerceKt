package com.example.myfirstecommercekt.ui.screens.checkout

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class CheckoutViewModel @Inject constructor() : ViewModel() {

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

    fun pay(toHome: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(4000)
            _success.value = true
            toHome()
        }
    }
}