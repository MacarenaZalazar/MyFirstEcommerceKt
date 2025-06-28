package com.example.myfirstecommercekt.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myfirstecommercekt.ui.screens.CheckoutScreen
import com.example.myfirstecommercekt.ui.screens.ForgotPassScreen
import com.example.myfirstecommercekt.ui.screens.LoginScreen
import com.example.myfirstecommercekt.ui.screens.ProductScreen
import com.example.myfirstecommercekt.ui.screens.ProfileScreen
import com.example.myfirstecommercekt.ui.screens.RegisterScreen
import com.example.myfirstecommercekt.ui.screens.ShoppingCartScreen
import com.example.myfirstecommercekt.ui.screens.SplashScreen
import com.example.myfirstecommercekt.viewmodel.CheckoutViewModel
import com.example.myfirstecommercekt.viewmodel.ForgotPassViewModel
import com.example.myfirstecommercekt.viewmodel.LoginViewModel
import com.example.myfirstecommercekt.viewmodel.ProductsViewModel
import com.example.myfirstecommercekt.viewmodel.ProfileViewModel
import com.example.myfirstecommercekt.viewmodel.RegisterViewModel
import com.example.myfirstecommercekt.viewmodel.ShoppingCartViewModel
import com.example.myfirstecommercekt.viewmodel.SplashViewModel


@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController, startDestination = SplashScreenRoute, modifier = modifier
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
            LoginScreen(
                viewModel = viewModel,
                toHome = { navController.navigate(ProductsScreenRoute) },
                forgotPass = { navController.navigate(ForgotPassScreenRoute) })
        }

        composable<RegisterScreenRoute> {
            val viewModel = hiltViewModel<RegisterViewModel>()
            RegisterScreen(viewModel = viewModel, goHome = {
                navController.navigate(
                    ProductsScreenRoute
                )
            })
        }

        composable<ProductsScreenRoute> {
            val productsViewModel = hiltViewModel<ProductsViewModel>()
            val cartViewModel = hiltViewModel<ShoppingCartViewModel>()

            ProductScreen(
                productsViewModel = productsViewModel,
                cartViewModel = cartViewModel,
                toCart = {
                    navController.navigate(
                        ShoppingCartScreenRoute
                    )
                })

        }

        composable<ShoppingCartScreenRoute> {
            val viewModel = hiltViewModel<ShoppingCartViewModel>()
            ShoppingCartScreen(
                viewModel = viewModel, toProducts = { navController.navigate(ProductsScreenRoute) })
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
            ForgotPassScreen(viewModel) { navController.navigate(LogInScreenRoute) }
        }
    }
}