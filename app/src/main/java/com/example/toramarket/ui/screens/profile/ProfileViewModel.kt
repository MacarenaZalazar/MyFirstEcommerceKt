package com.example.toramarket.ui.screens.profile

import android.app.*
import android.content.*
import android.net.*
import android.util.*
import androidx.lifecycle.*
import com.example.toramarket.domain.image.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.utils.helpers.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.*

@HiltViewModel()
class ProfileViewModel @Inject constructor(
    val myApplication: Application,
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val getUserEmailUseCase: GetUserEmailUseCase
) : ViewModel() {

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

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _success = MutableStateFlow<Boolean>(true)
    val success: MutableStateFlow<Boolean> = _success

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                var loggedEmail: String = getUserEmailUseCase.invoke() ?: ""
                if (loggedEmail.isEmpty()) _success.value = false
                else {
                    val response = getUserByEmailUseCase(email = loggedEmail)
                    val profile = response.body()
                    if (profile != null) {
                        _image.value = profile.userImageUrl ?: ""
                        _name.value = profile.fullName
                        _email.value = profile.email
                        _password.value = "********"
                    }
                }
            } catch (e: Exception) {
                Log.d("API_RESPONSE", e.message.toString())
            }
            _isLoading.value = false
        }
    }

    fun editProfile() {
        _password.value = ""
        _edit.value = true
    }

    fun updateProfile() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val updated = updateUserUseCase.invoke(
                    _email.value, _name.value, hashPasswordSHA256(_password.value), _image.value
                )
                val data = updated.body()
                if (data != null) {
                    _image.value = data.userImageUrl ?: ""
                    _name.value = data.fullName
                    _email.value = data.email
                    _password.value = "********"
                    _confirmPassword.value = ""
                    _edit.value = false
                }
            } catch (e: Exception) {
                Log.d("API_RESPONSE", e.message.toString())
            }
            _isLoading.value = false

        }
    }

    fun onRegisterChange(name: String, email: String, password: String, confirmPassword: String) {
        _name.value = name
        _password.value = password
        _confirmPassword.value = confirmPassword
        _isFormValid.value =
            isValidName(name) && isValidEmail(email) && isValidPassword(password) && password == confirmPassword
    }

    fun uploadImage(uri: Uri, context: Context) {
        viewModelScope.launch {
            val result = uploadImageUseCase.invoke(uri)
            if (result?.isNotEmpty() == true) _image.value = result
        }
    }

}