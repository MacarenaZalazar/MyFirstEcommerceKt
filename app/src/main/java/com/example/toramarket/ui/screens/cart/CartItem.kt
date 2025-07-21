package com.example.toramarket.ui.screens.cart

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.example.toramarket.R
import com.example.toramarket.data.local.entity.*

@Composable
fun CartItem(
    item: CartItemWithProduct,
    viewModel: CartViewModel,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                Modifier.padding(dimensionResource(R.dimen.padding_16)),
                verticalArrangement = Arrangement.Center
            ) {
                Text(item.product.name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_8)))
                Text("$${"%.2f".format(item.product.price)}", fontWeight = FontWeight.Bold)
            }

            Box(
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .padding(horizontal = dimensionResource(R.dimen.padding_16))
                    .clip(RoundedCornerShape(50))
                    .align(
                        Alignment.CenterVertically
                    ),
            ) {
                Row(
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.background
                    ), verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.removeFromCart(item.product.id) },
                    ) {
                        if (item.cartItem.quantity > 1) {
                            Icon(
                                imageVector = Icons.Filled.Remove,
                                contentDescription = stringResource(R.string.agregar)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = stringResource(R.string.eliminar)
                            )
                        }
                    }
                    Text(item.cartItem.quantity.toString())
                    IconButton(
                        onClick = { viewModel.addToCart(item.product.id) },

                        ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.agregar)
                        )
                    }
                }
            }
        }

    }
}