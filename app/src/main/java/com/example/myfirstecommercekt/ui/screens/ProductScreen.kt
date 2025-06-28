package com.example.myfirstecommercekt.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfirstecommercekt.ui.components.Product
import com.example.myfirstecommercekt.viewmodel.ProductsViewModel
import com.example.myfirstecommercekt.viewmodel.ShoppingCartViewModel

@Composable
fun ProductScreen(
    productsViewModel: ProductsViewModel,
    cartViewModel: ShoppingCartViewModel,
    toCart: () -> Unit
) {
    val products = listOf<String>("bananas", "manzanas", "peras ")

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Listado de productos")
        ProductList(products)
    }
}

@Composable
fun ProductList(products: List<String>) {
    LazyColumn() {
        items(products) { product ->
            Product()
        }
    }
}
