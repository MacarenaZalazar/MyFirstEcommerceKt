package com.example.toramarket.ui.screens.profile

import android.net.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import coil.network.*
import com.example.toramarket.domain.image.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.ui.*
import com.example.toramarket.utils.helpers.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import okio.*
import javax.inject.*

@HiltViewModel()
class ProfileViewModel @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {
    var uiState by mutableStateOf<UIState<Boolean>>(UIState.Loading)

    private val _name = MutableStateFlow<String>("")
    val name: MutableStateFlow<String> = _name

    private val _email = MutableStateFlow<String>("")
    val email: MutableStateFlow<String> = _email

    private val _password = MutableStateFlow<String>("")
    val password: MutableStateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow<String>("")
    val confirmPassword: MutableStateFlow<String> = _confirmPassword

    private val _image = MutableStateFlow<String>("")
    val image: MutableStateFlow<String> = _image

    private val _edit = MutableStateFlow<Boolean>(false)
    val edit: MutableStateFlow<Boolean> = _edit

    private val _isFormValid = MutableStateFlow<Boolean>(false)
    val isFormValid: MutableStateFlow<Boolean> = _isFormValid

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success
    fun getUser() {
        viewModelScope.launch {
            try {
                var loggedEmail: String = getUserEmailUseCase.invoke()

                if (loggedEmail.isEmpty()) uiState =
                    UIState.Error("No se pudo recuperar el usuario")
                else {

                    val response = getUserByEmailUseCase(email = loggedEmail)
                    val profile = response.body()
                    if (profile == null) uiState =
                        UIState.Error("No se pudo recuperar el usuario")
                    else {
                        _image.value = profile.userImageUrl ?: ""
                        _name.value = profile.fullName
                        _email.value = profile.email
                        _password.value = "********"
                        uiState = UIState.Success(true)
                    }
                }
            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a internet")
            } catch (e: HttpException) {
                uiState = UIState.Error("Error del servidor ${e.message}")
            } catch (e: Exception) {
                uiState = UIState.Error("Ocurrió un error inesperado")
            }

        }
    }

    fun editProfile() {
        _password.value = ""
        _edit.value = true
    }

    fun updateProfile() {
        viewModelScope.launch {
            try {
                uiState = UIState.Loading
                val updated = updateUserUseCase.invoke(
                    _email.value, _name.value, hashPasswordSHA256(_password.value), _image.value
                )
                val data = updated.body()
                if (data != null) {
                    uiState = UIState.Success(true)
                    _image.value = data.userImageUrl ?: ""
                    _name.value = data.fullName
                    _email.value = data.email
                    _password.value = "********"
                    _confirmPassword.value = ""
                    _confirmPassword.value = ""
                    _edit.value = false
                }
            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a internet")
            } catch (e: HttpException) {
                uiState = UIState.Error("Error del servidor: ${e.message}")
            } catch (e: Exception) {
                uiState = UIState.Error("Ocurrió un error inesperado")
            }

        }
    }

    fun onRegisterChange(name: String, email: String, password: String, confirmPassword: String) {
        _name.value = name
        _password.value = password
        _confirmPassword.value = confirmPassword
        _isFormValid.value =
            isValidName(name) && isValidEmail(email) && isValidPassword(password) && password == confirmPassword
    }

    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            try {
                uiState = UIState.Loading
                val result = uploadImageUseCase.invoke(uri)
                if (result?.isNotEmpty() == true) _image.value = result
                uiState = UIState.Success(true)
            } catch (e: IOException) {
                uiState = UIState.Error("Sin conexión a internet")
            } catch (e: HttpException) {
                uiState = UIState.Error("Error del servidor: ${e.message}")
            } catch (e: Exception) {
                uiState = UIState.Error("Ocurrió un error inesperado")
            }
        }
    }

    fun logOut(toSplash: () -> Unit) {
        viewModelScope.launch {
            try {
                logOutUseCase.invoke()
                toSplash()
            } catch (e: Exception) {

            }
        }

    }

}