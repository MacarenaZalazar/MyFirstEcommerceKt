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
            SplashScreen(
                toLogin = { navController.navigate(LogInScreenRoute) },
                toRegister = { navController.navigate(RegisterScreenRoute) },
                toProducts = { navController.navigate(ProductsScreenRoute) { popUpTo(0) } })

        }

        composable<LogInScreenRoute> {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = viewModel,
                toHome = { navController.navigate(ProductsScreenRoute) { popUpTo(0) } },
                forgotPass = { navController.navigate(ForgotPassScreenRoute) })
        }

        composable<RegisterScreenRoute> {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(viewModel = viewModel, toLogin = {
                navController.navigate(
                    LogInScreenRoute
                ) { popUpTo(0) }
            })
        }

        composable<ProductsScreenRoute> {
            val productsViewModel = hiltViewModel<ProductsViewModel>()
            ProductScreen(
                viewModel = productsViewModel
            )

        }

        composable<CartScreenRoute> {
            val cartViewModel = hiltViewModel<CartViewModel>()
            val checkoutVieModel = hiltViewModel<CheckoutViewModel>()
            CartScreen(
                cartViewModel = cartViewModel,
                toProducts = { navController.navigate(ProductsScreenRoute) },
                toCheckout = { navController.navigate(CheckoutScreenRoute) }
            )
        }

        composable<ProfileScreenRoute> {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(viewModel) { navController.navigate(SplashScreenRoute) }
        }

        composable<OrdersScreenRoute> {
            val viewModel = hiltViewModel<OrdersViewModel>()
            OrdersScreen(viewModel)
        }


        composable<CheckoutScreenRoute> {
            val viewModel = hiltViewModel<CheckoutViewModel>()
            CheckoutScreen(
                viewModel,
                goHome = {
                    navController.navigate(ProductsScreenRoute) {
                        popUpTo(ProductsScreenRoute) { inclusive = true }
                    }
                },
                goBack = { navController.popBackStack() }
            )
        }

        composable<ForgotPassScreenRoute> {
            val viewModel = hiltViewModel<ForgotPassViewModel>()
            ForgotPassScreen() { navController.navigate(LogInScreenRoute) }
        }
    }
}