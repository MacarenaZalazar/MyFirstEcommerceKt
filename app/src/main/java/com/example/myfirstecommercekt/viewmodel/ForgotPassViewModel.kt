package com.example.myfirstecommercekt.viewmodel

import androidx.lifecycle.*
import com.example.myfirstecommercekt.utils.helpers.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel
class ForgotPassViewModel @Inject constructor() : ViewModel() {
    private val _email = MutableStateFlow<String>("")
    val email: MutableStateFlow<String> = _email

    private val _password = MutableStateFlow<String>("")
    val password: MutableStateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow<String>("")
    val confirmPassword: MutableStateFlow<String> = _confirmPassword

    private val _isFormValid = MutableStateFlow<Boolean>(false)
    val isFormValid: MutableStateFlow<Boolean> = _isFormValid

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success

    fun onRegisterChange(email: String, password: String, confirmPassword: String) {
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword
        _isFormValid.value =
            isValidEmail(email) && isValidPassword(password) && password == confirmPassword
    }

    fun updatePassword(toLogin: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(4000)
            _success.value = true
            toLogin()
//            _isLoading.value = false
        }
    }
}