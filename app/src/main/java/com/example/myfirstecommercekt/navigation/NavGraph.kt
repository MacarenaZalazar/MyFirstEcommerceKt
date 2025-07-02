package com.example.myfirstecommercekt.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.myfirstecommercekt.ui.screens.cart.*
import com.example.myfirstecommercekt.ui.screens.checkout.*
import com.example.myfirstecommercekt.ui.screens.forgotPass.*
import com.example.myfirstecommercekt.ui.screens.login.*
import com.example.myfirstecommercekt.ui.screens.products.*
import com.example.myfirstecommercekt.ui.screens.profile.*
import com.example.myfirstecommercekt.ui.screens.register.*
import com.example.myfirstecommercekt.ui.screens.splash.*


@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController, startDestination = SplashScreenRoute, modifier = modifier
    ) {

        composable<SplashScreenRoute> {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                toLogin = { navController.navigate(LogInScreenRoute) },
                toRegister = { navController.navigate(RegisterScreenRoute) })

        }

        composable<LogInScreenRoute> {
            val viewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                viewModel = viewModel,
                toHome = { navController.navigate(ProductsScreenRoute) },
                forgotPass = { navController.navigate(ForgotPassScreenRoute) })
        }

        composable<RegisterScreenRoute> {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(viewModel = viewModel, toLogin = {
                navController.navigate(
                    LogInScreenRoute
                )
            })
        }

        composable<ProductsScreenRoute> {
            val productsViewModel = hiltViewModel<ProductsViewModel>()
            val cartViewModel = hiltViewModel<CartViewModel>()

            ProductScreen(
                productsViewModel = productsViewModel, cartViewModel = cartViewModel, toCart = {
                    navController.navigate(
                        CartScreenRoute
                    )
                })

        }

        composable<CartScreenRoute> {
            val cartViewModel = hiltViewModel<CartViewModel>()
            val checkoutVieModel = hiltViewModel<CheckoutViewModel>()
            CartScreen(
                cartViewModel = cartViewModel,
                checkoutVieModel = checkoutVieModel,
                toProducts = { navController.navigate(ProductsScreenRoute) })
        }

        composable<ProfileScreenRoute> {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(viewModel)
        }

        composable<CheckoutScreenRoute> {
            val viewModel = hiltViewModel<CheckoutViewModel>()
            CheckoutScreen(viewModel)
        }

        composable<ForgotPassScreenRoute> {
            val viewModel = hiltViewModel<ForgotPassViewModel>()
            ForgotPassScreen() { navController.navigate(LogInScreenRoute) }
        }
    }
}