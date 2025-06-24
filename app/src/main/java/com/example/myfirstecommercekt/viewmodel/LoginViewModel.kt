package com.example.myfirstecommercekt.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel : ViewModel() {
    private val _email = MutableStateFlow<String>("");
    val email : MutableStateFlow<String> = _email

}