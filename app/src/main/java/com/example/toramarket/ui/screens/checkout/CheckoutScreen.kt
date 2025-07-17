package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import com.example.toramarket.ui.*
import com.example.toramarket.ui.navigation.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.uiState
    var paymentMethod by rememberSaveable { mutableStateOf("cash") }
    val isValid by viewModel.isValid.collectAsState()

    val showDialog by viewModel.showDialog.collectAsState()
    val dialogMessage by viewModel.dialogMessage.collectAsState()

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.getOrder()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.closeDialog() },
            text = { Text(dialogMessage) },
            confirmButton = {
                Button(onClick = {
                    viewModel.closeDialog()
                    navController.navigate(ProductsScreenRoute) {
                        popUpTo(ProductsScreenRoute) { inclusive = true }
                    }
                }) {
                    Text("OK")
                }
            }
        )
    }

    when (state) {
        is UIState.Loading -> {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                CircularProgressIndicator(
                    Modifier.align(Alignment.Center)
                )
            }
        }

        is UIState.Success -> {
            Scaffold(topBar = {
                CenterAlignedTopAppBar(
                   
                    modifier = Modifier.shadow(4.dp),
                    title = { Text("Confirmá tu pedido") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    })
            }, bottomBar = {
                BottomAppBar(tonalElevation = 4.dp) {
                    Button(
                        onClick = { viewModel.pay() },
                        enabled = isValid,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Text("Pedir ahora")
                    }
                }
            }) { it ->

                Column(
                    Modifier
                        .padding(it)
                        .padding(20.dp)
                        .verticalScroll(scrollState)
                ) {
                    Resume(state.data)
                    PaymentMethodSelector(paymentMethod) { paymentMethod = it }
                    if (paymentMethod == "card") CheckoutCardForm(viewModel)


                }
            }


        }

        is UIState.Error -> {
            // Show error message
        }

    }

}


