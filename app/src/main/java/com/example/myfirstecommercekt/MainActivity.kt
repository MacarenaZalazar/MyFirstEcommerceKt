package com.example.myfirstecommercekt

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myfirstecommercekt.presentation.LoginScreen
import com.example.myfirstecommercekt.ui.theme.MyFirstEcommerceKtTheme
import com.example.myfirstecommercekt.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstEcommerceKtTheme {
                LoginScreen(viewModel = LoginViewModel())
            }
        }
    }
}
