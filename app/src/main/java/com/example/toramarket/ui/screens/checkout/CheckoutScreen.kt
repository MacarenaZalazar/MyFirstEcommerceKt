package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.*
import androidx.navigation.*
import com.example.toramarket.R
import com.example.toramarket.ui.*
import com.example.toramarket.ui.navigation.*
import com.example.toramarket.utils.helpers.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    viewModel: CheckoutViewModel = hiltViewModel(),
    navController: NavController
) {

    val state = viewModel.uiState
    val paymentMethod by viewModel.paymentMethod.collectAsState()
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
                    Text(stringResource(R.string.ok))
                }
            }
        )
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
            Scaffold(topBar = {
                CenterAlignedTopAppBar(
                    modifier = Modifier.shadow(dimensionResource(R.dimen.padding_4)),
                    title = { Text(stringResource(R.string.confirm_tu_pedido)) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    })
            }, bottomBar = {
                BottomAppBar(tonalElevation = dimensionResource(R.dimen.padding_4)) {
                    Button(
                        onClick = { viewModel.pay() },
                        enabled = isValid,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(stringResource(R.string.pedir_ahora))
                    }
                }
            }) { it ->

                Column(
                    Modifier
                        .padding(it)
                        .padding(20.dp)
                        .verticalScroll(scrollState)
                ) {
                    CheckoutResume(state.data)
                    PaymentMethodSelector(paymentMethod) { viewModel.changePaymentMethod(it) }
                    if (paymentMethod == PaymentMethod.CARD) CheckoutCardForm(viewModel)


                }
            }


        }

        is UIState.Error -> {
            // Show error message
        }

    }

}


