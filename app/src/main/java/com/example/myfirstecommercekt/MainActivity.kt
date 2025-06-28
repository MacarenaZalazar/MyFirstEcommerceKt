package com.example.myfirstecommercekt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myfirstecommercekt.ui.components.BottomNavBar
import com.example.myfirstecommercekt.navigation.LogInScreenRoute
import com.example.myfirstecommercekt.navigation.NavGraph
import com.example.myfirstecommercekt.navigation.RegisterScreenRoute
import com.example.myfirstecommercekt.navigation.SplashScreenRoute
import com.example.myfirstecommercekt.ui.theme.MyFirstEcommerceKtTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.myfirstecommercekt.navigation.CheckoutScreenRoute
import com.example.myfirstecommercekt.navigation.ForgotPassScreenRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyFirstEcommerceKtTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val showBottomBar = when (currentRoute) {
                    SplashScreenRoute::class.qualifiedName,
                    LogInScreenRoute::class.qualifiedName,
                    RegisterScreenRoute::class.qualifiedName,
                    CheckoutScreenRoute::class.qualifiedName,
                    ForgotPassScreenRoute::class.qualifiedName -> false

                    else -> true
                }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            BottomNavBar(navController)
                        }
                    }) { innerPadding ->
                    NavGraph(
                        navController = navController, modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}