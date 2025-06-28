package com.example.myfirstecommercekt.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myfirstecommercekt.navigation.ProductsScreenRoute
import com.example.myfirstecommercekt.navigation.ProfileScreenRoute
import com.example.myfirstecommercekt.navigation.ShoppingCartScreenRoute


data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun BottomNavBar(controller: NavHostController) {
    val items = listOf(
        BottomNavItem(
            label = "",
            route = ProductsScreenRoute::class.qualifiedName ?: "products",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            label = "",
            route = ShoppingCartScreenRoute::class.qualifiedName ?: "cart",
            icon = Icons.Default.ShoppingCart
        ),
        BottomNavItem(
            label = "",
            route = ProfileScreenRoute::class.qualifiedName ?: "profile",
            icon = Icons.Default.Face
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