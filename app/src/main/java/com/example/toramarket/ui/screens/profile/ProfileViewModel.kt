package com.example.toramarket.ui.screens.profile

import android.net.*
import android.util.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import coil.network.*
import com.example.toramarket.data.remote.dto.*
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
    private val updateUserNameUseCase: UpdateUserNameUseCase,
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase,
    private val updateUserImgUseCase: UpdateUserImgUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {
    var uiState by mutableStateOf<UIState<Boolean>>(UIState.Loading)

    private val _profile = MutableStateFlow<UserDto?>(null)
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

    private val _editName = MutableStateFlow<Boolean>(false)
    val editName: MutableStateFlow<Boolean> = _editName

    private val _editPassword = MutableStateFlow<Boolean>(false)
    val editPassword: MutableStateFlow<Boolean> = _editPassword

    private val _isFormValid = MutableStateFlow<Boolean>(false)
    val isFormValid: MutableStateFlow<Boolean> = _isFormValid

    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage = _snackbarMessage

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

                        _profile.value = profile
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

    fun toggleEditName() {
        _editPassword.value = false
        _editName.value = true
    }

    fun toggleEditPassword() {
        _editName.value = false
        _editPassword.value = true
        _name.value = _profile.value?.fullName ?: ""
        _password.value = ""
    }

    fun cancelEdit() {
        _editName.value = false
        _editPassword.value = false
        _password.value = "********"
    }


    fun updatePassword() {
        viewModelScope.launch {
            try {
                uiState = UIState.Loading
                val updated = updateUserPasswordUseCase.invoke(
                    _email.value, hashPasswordSHA256(_password.value)
                )
                val data = updated.body()
                if (data != null) {
                    uiState = UIState.Success(true)
                    _name.value = data.fullName
                    _email.value = data.email
                    _password.value = "********"
                    _confirmPassword.value = ""
                    _confirmPassword.value = ""
                    _editPassword.value = false
                }
                _snackbarMessage.emit("Contraseña actualizada correctamente")
            } catch (e: IOException) {
                _snackbarMessage.emit("Sin conexión a internet")
            } catch (e: HttpException) {
                _snackbarMessage.emit("Error del servidor: ${e.message}")
            } catch (e: Exception) {
                _snackbarMessage.emit("Ocurrió un error inesperado")
            } finally {
                uiState = UIState.Success(true)
            }

        }
    }

    fun updateName() {
        viewModelScope.launch {
            try {
                uiState = UIState.Loading
                val updated = updateUserNameUseCase.invoke(
                    _email.value, _name.value
                )
                val data = updated.body()
                if (data != null) {
                    uiState = UIState.Success(true)
                    _name.value = data.fullName
                    _email.value = data.email
                    _password.value = "********"
                    _confirmPassword.value = ""
                    _confirmPassword.value = ""
                    _editName.value = false
                }
                _snackbarMessage.emit("Nombre actualizado correctamente")
            } catch (e: IOException) {
                _snackbarMessage.emit("Sin conexión a internet")
            } catch (e: HttpException) {
                _snackbarMessage.emit("Error del servidor: ${e.message}")
            } catch (e: Exception) {
                _snackbarMessage.emit("Ocurrió un error inesperado")
            } finally {
                uiState = UIState.Success(true)
            }

        }
    }

    fun onRegisterChange(name: String) {
        _name.value = name
        _isFormValid.value =
            isValidName(name)
    }

    fun onRegisterChange(password: String, confirmPassword: String) {
        _password.value = password
        _confirmPassword.value = confirmPassword
        _isFormValid.value =
            isValidPassword(password) && password == confirmPassword
    }


    fun uploadImage(uri: Uri) {
        viewModelScope.launch {
            try {
                uiState = UIState.Loading
                Log.d("ProfileViewModel", "Uploading image: $uri")
                val result = uploadImageUseCase.invoke(uri)
                if (result?.isNotEmpty() == true) {
                    updateUserImgUseCase.invoke(
                        _email.value, result
                    )
                    _image.value = result
                }
                _snackbarMessage.emit("Foto de perfil actualizada correctamente")
            } catch (e: IOException) {
                _snackbarMessage.emit("Sin conexión a internet")
            } catch (e: HttpException) {
                _snackbarMessage.emit("Error del servidor: ${e.message}")
            } catch (e: Exception) {
                _snackbarMessage.emit("Ocurrió un error inesperado")
            } finally {
                uiState = UIState.Success(true)
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