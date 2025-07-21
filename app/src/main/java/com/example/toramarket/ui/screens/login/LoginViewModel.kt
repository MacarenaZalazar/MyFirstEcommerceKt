package com.example.toramarket.ui.screens.login

import androidx.lifecycle.*
import com.example.toramarket.data.local.*
import com.example.toramarket.domain.auth.*
import com.example.toramarket.utils.helpers.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel()
class LoginViewModel @Inject constructor(
    private val userDataStore: UserDataStore,
    private val authenticateUserUseCase: AuthenticateUserUseCase
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
            try {
                val res = authenticateUserUseCase.invoke(
                    _email.value.lowercase(),
                    hashPasswordSHA256(_password.value)
                )
                if (res.isSuccessful) {
                    val user = res.body()!!.user
                    delay(4000)
                    userDataStore.saveUser(id = user.id, name = user.fullName, email = user.email)
                    _success.value = true
                    toHome()
                }
            } catch (e: Exception) {
                _isLoading.value = false

            }
        }
    }

}