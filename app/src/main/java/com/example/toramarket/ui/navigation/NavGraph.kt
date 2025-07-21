package com.example.toramarket.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.toramarket.ui.screens.cart.*
import com.example.toramarket.ui.screens.checkout.*
import com.example.toramarket.ui.screens.forgotPass.*
import com.example.toramarket.ui.screens.login.*
import com.example.toramarket.ui.screens.orders.*
import com.example.toramarket.ui.screens.products.*
import com.example.toramarket.ui.screens.profile.*
import com.example.toramarket.ui.screens.register.*
import com.example.toramarket.ui.screens.splash.*


@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController, startDestination = SplashScreenRoute, modifier = modifier
    ) {

        composable<SplashScreenRoute> {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(viewModel = viewModel, navController = navController)
        }

        composable<LogInScreenRoute> {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = viewModel, navController = navController
            )
        }

        composable<RegisterScreenRoute> {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(viewModel = viewModel, navController)
        }

        composable<ProductsScreenRoute> {
            val productsViewModel = hiltViewModel<ProductsViewModel>()
            ProductScreen(
                viewModel = productsViewModel
            )

        }

        composable<CartScreenRoute> {
            val cartViewModel = hiltViewModel<CartViewModel>()
            CartScreen(
                viewModel = cartViewModel,
                navController = navController
            )
        }

        composable<ProfileScreenRoute> {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(viewModel, navController)
        }

        composable<OrdersScreenRoute> {
            val viewModel = hiltViewModel<OrdersViewModel>()
            OrdersScreen(viewModel, navController)
        }


        composable<CheckoutScreenRoute> {
            val viewModel = hiltViewModel<CheckoutViewModel>()
            CheckoutScreen(
                viewModel,
                navController
            )
        }

        composable<ForgotPassScreenRoute> {
            val viewModel = hiltViewModel<ForgotPassViewModel>()
            ForgotPassScreen(viewModel, navController)
        }
    }
}

