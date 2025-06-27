package com.example.myfirstecommercekt.viewmodel

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstecommercekt.utils.isValidEmail
import com.example.myfirstecommercekt.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel()
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _email = MutableStateFlow<String>("");
    val email: MutableStateFlow<String> = _email

    private val _password = MutableStateFlow<String>("");
    val password: MutableStateFlow<String> = _password

    private var _loginEnabled = MutableStateFlow<Boolean>(false)
    val loginEnabled = _loginEnabled

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    fun onLoginChange(email: String, password: String) {
        _email.value = email
        if (password.length > 15) return
        _password.value = password
        _loginEnabled.value = isValidEmail(email) && isValidPassword(password)
    }

    fun logIn() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(4000)
            _isLoading.value = false
        }
    }

}