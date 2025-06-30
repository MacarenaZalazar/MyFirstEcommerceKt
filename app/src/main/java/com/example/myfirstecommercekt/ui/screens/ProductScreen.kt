package com.example.myfirstecommercekt.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.myfirstecommercekt.data.local.entity.*
import com.example.myfirstecommercekt.ui.components.*
import com.example.myfirstecommercekt.viewmodel.*

@Composable
fun ProductScreen(
    productsViewModel: ProductsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    toCart: () -> Unit
) {
    val products by productsViewModel.filteredProducts.collectAsState()
    val isLoading by productsViewModel.isLoading.collectAsState()
    val filter by productsViewModel.filter.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Listado de productos")
                SimpleText(
                    value = filter,
                    onChange = { productsViewModel.filterProducts(it) },
                    label = "Encontrá tu producto",
                    error = false
                )
                ProductList(products, cartViewModel)
            }
        }
    }
}

@Composable
fun ProductList(products: List<ProductEntity>, viewModel: CartViewModel) {
    LazyColumn() {
        items(products) { it ->
            Product(item = it, addToCart = { viewModel.addToCart(it) })
        }
    }
}
