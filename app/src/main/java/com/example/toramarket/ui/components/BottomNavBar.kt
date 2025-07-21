package com.example.toramarket.ui.components

import androidx.compose.material.icons.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.res.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.toramarket.R
import com.example.toramarket.ui.navigation.*


data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun BottomNavBar(controller: NavHostController) {
    val items = listOf(
        BottomNavItem(
            label = stringResource(R.string.inicio),
            route = ProductsScreenRoute::class.qualifiedName ?: stringResource(R.string.products),
            icon = Icons.Rounded.Home
        ),
        BottomNavItem(
            label = stringResource(R.string.carrito),
            route = CartScreenRoute::class.qualifiedName ?: stringResource(R.string.cart),
            icon = Icons.Rounded.ShoppingCart
        ),
        BottomNavItem(
            label = stringResource(R.string.pedidos),
            route = OrdersScreenRoute::class.qualifiedName ?: stringResource(R.string.orders),
            icon = Icons.Rounded.Receipt
        ),
        BottomNavItem(
            label = stringResource(R.string.perfil),
            route = ProfileScreenRoute::class.qualifiedName ?: stringResource(R.string.profile),
            icon = Icons.Rounded.AccountCircle
        )
    )

    val navBackStackEntry = controller.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        controller.navigate(item.route) {
                            popUpTo(0) { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}