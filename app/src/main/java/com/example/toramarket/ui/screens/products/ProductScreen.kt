package com.example.toramarket.ui.screens.products

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
import com.example.toramarket.ui.*
import com.example.toramarket.utils.data.*

@Composable
fun ProductScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
    val filter = viewModel.filter
    val selectedCategory = viewModel.selectedCategory

    LaunchedEffect(Unit) {
        viewModel.loadProducts(refresh = true)
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp), contentAlignment = Alignment.Center
    ) {
        when (state) {
            is UIState.Loading -> {

                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }

            is UIState.Success -> {
                val products = viewModel.filteredProducts
                val filter by remember { mutableStateOf("") }
                val categories = products.map { it.category }.distinct()

                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        value = filter,
                        onValueChange = {},
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
                    CategoriesSelector(
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onCategorySelected = { viewModel.selectedCategory = it })

                    ProductList(products, viewModel)
                }
            }

            is UIState.Error -> Text(state.message)
        }
    }
}

@Composable
fun ProductList(products: List<Product>, viewModel: ProductsViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { it ->
            Product(item = it, addToCart = { viewModel.addToCart(it.id) })
        }
    }
}
