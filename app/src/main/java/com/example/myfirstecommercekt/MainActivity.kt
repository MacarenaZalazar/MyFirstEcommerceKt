package com.example.myfirstecommercekt

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.myfirstecommercekt.navigation.NavGraph
import com.example.myfirstecommercekt.ui.theme.MyFirstEcommerceKtTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstEcommerceKtTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
