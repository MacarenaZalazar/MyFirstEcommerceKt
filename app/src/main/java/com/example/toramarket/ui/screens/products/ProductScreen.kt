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
import androidx.compose.ui.res.*
import androidx.hilt.navigation.compose.*
import com.example.toramarket.R
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
                        title = { Text(stringResource(R.string.productos)) },
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
                        .padding(horizontal = dimensionResource(R.dimen.padding_16))
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_8))
                            .fillMaxWidth(),
                        value = filter,
                        onValueChange = { viewModel.onFilterChange(it) },
                        label = { Text(stringResource(R.string.encontr_tu_producto)) },
                        singleLine = true,
                        trailingIcon =
                            {
                                Icon(
                                    contentDescription = stringResource(R.string.buscar),
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
                        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_8)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8))
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
                        title = { Text(stringResource(R.string.productos)) },
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