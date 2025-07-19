package com.example.toramarket.ui.screens.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import com.example.toramarket.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
    val filter = viewModel.filter
    val selectedCategory = viewModel.selectedCategory

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.loadProducts(refresh = true)
        viewModel.snackbarMessage.collect { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    when (state) {
        is UIState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }
        }

        is UIState.Success -> {
            val products = viewModel.filteredProducts
            val categories = products.map { it.category }.distinct().sorted()
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Productos") },
                        actions = {
                            IconButton(onClick = { viewModel.loadProducts(true) }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                            }
                        }
                    )
                }
            ) { it ->
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(horizontal = 16.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        value = filter,
                        onValueChange = { viewModel.onFilterChange(it) },
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

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(products) { it ->
                            ProductItem(item = it, viewModel = viewModel)
                        }
                    }
                }
            }

        }

        is UIState.Error ->
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Productos") },
                        actions = {
                            IconButton(onClick = { viewModel.loadProducts(true) }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                            }
                        }
                    )
                }
            ) { it ->
                Box(
                    Modifier
                        .padding(it)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(state.message)
                }
            }
    }

}