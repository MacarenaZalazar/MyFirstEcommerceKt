package com.example.toramarket.ui.screens.cart

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.sharp.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.*
import androidx.navigation.*
import com.example.toramarket.R
import com.example.toramarket.ui.navigation.*

@Composable
fun EmptyCart(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_16)), contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.tu_carrito_se_encuentra_vac_o), fontSize = 25.sp)
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(dimensionResource(R.dimen.padding_16))
            ) {

                Icon(
                    imageVector = Icons.Sharp.ShoppingCart,
                    contentDescription = stringResource(R.string.carrito_vac_o),
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier.size(100.dp),
                )
            }
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
            Text(stringResource(R.string.sum_productos_y_comenz_tu_compra))
            Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_16)))
            Button(
                onClick = {
                    navController.navigate(ProductsScreenRoute) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            ) {
                Text(stringResource(R.string.buscar_productos))
            }
        }
    }
}