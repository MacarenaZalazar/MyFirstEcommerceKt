package com.example.myfirstecommercekt.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfirstecommercekt.presentation.LoginScreen
import com.example.myfirstecommercekt.presentation.RegisterScreen
import com.example.myfirstecommercekt.presentation.SplashScreen
import com.example.myfirstecommercekt.viewmodel.LoginViewModel
import com.example.myfirstecommercekt.viewmodel.RegisterViewModel
import com.example.myfirstecommercekt.viewmodel.SplashViewModel


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController, startDestination = SplashScreenRoute
    ) {
        composable<SplashScreenRoute> {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                viewModel = viewModel,
                toLogin = { navController.navigate(LogInScreenRoute) },
                toRegister = { navController.navigate(RegisterScreenRoute) })

        }
        composable<LogInScreenRoute> {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(viewModel=viewModel)
        }
        composable<RegisterScreenRoute> {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(viewModel = viewModel)
        }
    }
}