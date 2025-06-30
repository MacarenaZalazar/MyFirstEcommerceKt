package com.example.myfirstecommercekt.viewmodel

import android.util.*
import androidx.lifecycle.*
import com.example.myfirstecommercekt.data.*
import com.example.myfirstecommercekt.data.remote.dto.*
import com.example.myfirstecommercekt.data.repository.implementation.*
import com.example.myfirstecommercekt.utils.helpers.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel()
class LoginViewModel @Inject constructor(
    private val userDataStore: UserDataStore, private val repo: AuthRepositoryImpl
) : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    val email: MutableStateFlow<String> = _email

    private val _password = MutableStateFlow<String>("")
    val password: MutableStateFlow<String> = _password

    private var _loginEnabled = MutableStateFlow<Boolean>(false)
    val loginEnabled = _loginEnabled

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success

    fun onLoginChange(email: String, password: String) {
        _email.value = email
        if (password.length > 15) return
        _password.value = password
        _loginEnabled.value = isValidEmail(email) && isValidPassword(password)
    }

    fun logIn(toHome: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            val req = AuthRequest(_email.value, hashPasswordSHA256(_password.value))
            try {
                println("login!")
                val res = repo.login(req)
                Log.d("API_RESPONSE", "Respuesta del servidor: $res")
                if (res.isSuccessful) {
                    val user = res.body()!!
                    delay(4000)
                    userDataStore.saveUser(user.email, user.fullName)
                    _success.value = true
                    toHome()
                }
                _isLoading.value = false
            } catch (e: Exception) {
                Log.d("API_RESPONSE", { e.message }.toString())
                _isLoading.value = false

            }
        }
    }

}