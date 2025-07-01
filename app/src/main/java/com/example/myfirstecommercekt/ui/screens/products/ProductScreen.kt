package com.example.myfirstecommercekt.ui.screens.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.myfirstecommercekt.data.local.entity.*
import com.example.myfirstecommercekt.ui.components.*
import com.example.myfirstecommercekt.ui.screens.cart.*
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
            .padding(top = 16.dp, start = 16.dp, end = 16.dp), contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                Modifier.align(Alignment.Center)
            )
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    value = filter,
                    onValueChange = { productsViewModel.filterProducts(it) },
                    label = { Text("Encontrá tu producto") },
                    singleLine = true,
                    trailingIcon =
                        {
                            Icon(
                                contentDescription = "buscar",
                                imageVector = Icons.Outlined.Search, tint = Color.LightGray
                            )
                        }
                )


                ProductList(products, cartViewModel)
            }
        }
    }
}

@Composable
fun ProductList(products: List<ProductEntity>, viewModel: CartViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { it ->
            Product(item = it, addToCart = { viewModel.addToCart(it) })
        }
    }
}
