package com.example.toramarket

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.compose.*
import com.example.toramarket.ui.components.*
import com.example.toramarket.ui.navigation.*
import com.example.toramarket.ui.theme.*
import dagger.hilt.android.*

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