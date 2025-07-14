package com.example.toramarket.ui.screens.splash

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.ui.*
import dagger.hilt.android.lifecycle.*
import kotlinx.coroutines.*
import javax.inject.*

@HiltViewModel
class SplashViewModel @Inject constructor(private val isUserLoggedInUseCase: IsUserLoggedInUseCase) :
    ViewModel() {
    var uiState by mutableStateOf<UIState<Boolean>>(UIState.Loading)

    fun getLoggedUser() {
        viewModelScope.launch {
            try {
                val isLogged = isUserLoggedInUseCase.invoke()
                uiState = UIState.Success(isLogged)
            } catch (e: Exception) {
                uiState = UIState.Error("Ocurrió un error inesperado")
            }
        }
    }
}
