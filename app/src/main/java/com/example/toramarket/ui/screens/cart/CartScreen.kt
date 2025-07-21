package com.example.toramarket.ui.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.navigation.*
import com.example.toramarket.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navController: NavController
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val subtotal by viewModel.subtotal.collectAsState()
    val count by viewModel.count.collectAsState()


    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collect { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    if (cartItems.isEmpty()) {
        EmptyCart(navController)
    } else {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(stringResource(R.string.mi_carrito)) },
                    actions = {
                        Button(
                            onClick = { viewModel.clearCart() },
                        ) {
                            Text(stringResource(R.string.vaciar))
                        }
                    }
                )
            }

        ) { it ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(
                        horizontal = dimensionResource(R.dimen.padding_16),
                        vertical = dimensionResource(R.dimen.padding_16)
                    ),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8))
                ) {
                    items(cartItems) { it ->
                        CartItem(
                            item = it, viewModel = viewModel
                        )
                    }
                }
                CartSummary(count, subtotal, navController)
            }
        }
    }
}

