package com.example.toramarket.ui.screens.register

import androidx.lifecycle.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.data.repository.interfaces.*
import com.example.toramarket.utils.helpers.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel()
class RegisterViewModel @Inject constructor(private val repo: UserRepository) : ViewModel() {

    private val _name = MutableStateFlow<String>("")
    val name: MutableStateFlow<String> = _name

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

    fun onRegisterChange(name: String, email: String, password: String, confirmPassword: String) {
        _name.value = name
        _email.value = email
        _password.value = password
        _confirmPassword.value = confirmPassword
        _isFormValid.value =
            name.isNotEmpty() && isValidEmail(email) && isValidPassword(password) && password == confirmPassword
    }

    fun register(toHome: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repo.register(
                    UserRegisterDto(
                        email = _email.value,
                        fullName = _name.value,
                        encryptedPassword = hashPasswordSHA256(_password.value)
                    )
                )
                _success.value = true

                toHome()
            } catch (e: Exception) {
                _success.value = false
                _isLoading.value = false
            }
        }
    }
}