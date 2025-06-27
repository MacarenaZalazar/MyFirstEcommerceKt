package com.example.myfirstecommercekt.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel: ViewModel() {
    private val _email = MutableStateFlow<String>("");
    val email: MutableStateFlow<String> = _email

    private val _password = MutableStateFlow<String>("");
    val password: MutableStateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow<String>("");
    val confirmPassword: MutableStateFlow<String> = _confirmPassword

    fun onEmailChange(value: String) {
        _email.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }
    fun onConfirmPasswordChange(value: String){
        _confirmPassword.value = value
    }


}