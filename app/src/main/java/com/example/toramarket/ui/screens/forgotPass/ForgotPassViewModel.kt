package com.example.toramarket.ui.screens.forgotPass

import androidx.lifecycle.*
import coil.network.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.utils.helpers.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okio.*
import javax.inject.*

@HiltViewModel
class ForgotPassViewModel @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase
) :
    ViewModel() {

    private val _user = MutableStateFlow<Boolean>(false)
    val user: MutableStateFlow<Boolean> = _user

    private val _email = MutableStateFlow<String>("")
    val email: MutableStateFlow<String> = _email

    private val _password = MutableStateFlow<String>("")
    val password: MutableStateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow<String>("")
    val confirmPassword: MutableStateFlow<String> = _confirmPassword

    private val _isEmailValid = MutableStateFlow<Boolean>(false)
    val isEmailValid: MutableStateFlow<Boolean> = _isEmailValid

    private val _isFormValid = MutableStateFlow<Boolean>(false)
    val isFormValid: MutableStateFlow<Boolean> = _isFormValid

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage
    fun onPasswordChange(password: String, confirmPassword: String) {
        _password.value = password
        _confirmPassword.value = confirmPassword
        _isFormValid.value = isValidPassword(password) && password == confirmPassword
    }


    fun onEmailChange(email: String) {
        _email.value = email
        _isEmailValid.value = isValidEmail(email)
    }

    fun validateEmail() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val user = getUserByEmailUseCase.invoke(_email.value)
                if (user.isSuccessful) _user.value = true
                else _snackbarMessage.emit("Usuario no encontrado")
            } catch (e: IOException) {
                _snackbarMessage.emit("Sin conexión a internet")
            } catch (e: HttpException) {
                _snackbarMessage.emit("${e.message}")
            } catch (e: Exception) {
                _snackbarMessage.emit("Ocurrió un error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePassword(toLogin: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                updateUserPasswordUseCase.invoke(email = _email.value, password = _password.value)
                _snackbarMessage.emit("Contraseña actualizada correctamente")
            } catch (e: IOException) {
                _snackbarMessage.emit("Ocurrió un error actualizar la contraseña: ${e.message ?: "desconocido"}")
            }
            _success.value = true
            toLogin()
        }
    }
}