package com.example.toramarket.ui.screens.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import com.example.toramarket.R
import com.example.toramarket.ui.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = hiltViewModel(), navController: NavController
) {
    val state = viewModel.uiState

    val showSheet by viewModel.showSheet.collectAsState()

    val orderDetail by viewModel.orderDetail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOrders()
    }

    if (showSheet && orderDetail != null) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.closeDialog() },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            OrderDetail(orderDetail!!)
        }
    }


    when (state) {
        is UIState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(
                        top = dimensionResource(R.dimen.padding_16),
                        start = dimensionResource(R.dimen.padding_16),
                        end = dimensionResource(R.dimen.padding_16)
                    )
            ) {

                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }
        }

        is UIState.Success -> {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(R.string.mis_pedidos)) },
                        actions = {
                            IconButton(onClick = { viewModel.loadOrders() }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                            }
                        }
                    )
                }
            ) { it ->

                val orders = state.data
                if (orders.isEmpty()) {
                    Box(
                        Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {

                        Text(stringResource(R.string.no_se_encontraron_pedidos))
                    }
                } else {
                    LazyColumn(
                        Modifier
                            .padding(it)
                            .padding(
                                horizontal = dimensionResource(R.dimen.padding_16),
                                vertical = dimensionResource(R.dimen.padding_4)
                            )
                            .fillMaxSize(),
                        contentPadding = PaddingValues(vertical = dimensionResource(R.dimen.padding_8)),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_10))
                    ) {
                        items(orders) { it ->
                            Order(it, viewModel)
                        }
                    }

                }
            }

        }

        is UIState.Error ->
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(stringResource(R.string.mis_pedidos)) },
                        actions = {
                            IconButton(onClick = { viewModel.loadOrders() }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                            }
                        }
                    )
                }
            ) { it ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(it)

                ) {
                    Text(state.message, Modifier.align(Alignment.Center))
                }
            }

    }
}
