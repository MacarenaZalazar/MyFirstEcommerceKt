package com.example.toramarket.ui.screens.checkout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.hilt.navigation.compose.*
import com.example.toramarket.R
import com.example.toramarket.ui.components.*
import com.example.toramarket.utils.helpers.*

@Composable
fun CheckoutCardForm(viewModel: CheckoutViewModel = hiltViewModel()) {

    val name by viewModel.name.collectAsState()
    val number by viewModel.number.collectAsState()
    val expiration by viewModel.expiration.collectAsState()
    val cvv by viewModel.cvv.collectAsState()

    val cardType by viewModel.cardType.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_16))
    ) {
        Text(
            stringResource(R.string.por_favor_ingres_los_datos_de_tu_tarjeta),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_16)))

        SimpleText(
            value = number,
            onChange = {
                viewModel.onChange(
                    it,
                    name,
                    expiration,
                    cvv
                )
            },
            enabled = true,
            error = { validateCardNumber(it) },
            label = stringResource(R.string.n_mero_de_la_tarjeta),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_8)))

        SimpleText(
            value = name,
            onChange = {
                viewModel.onChange(
                    number,
                    it,
                    expiration,
                    cvv
                )
            },
            enabled = true,
            error = { validateName(it) },
            label = stringResource(R.string.nombre_como_figura_en_la_tarjeta),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_8)))
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_8))) {

            SimpleText(
                value = expiration,
                onChange = {
                    viewModel.onChange(
                        number,
                        name,
                        formatExpiryInput(it),
                        cvv
                    )
                },
                enabled = true,
                error = { validateExpiration(it) },
                label = stringResource(R.string.mm_yy),
                modifier = Modifier.weight(0.5f),
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_8)))

            SimpleText(
                value = cvv,
                onChange = {
                    viewModel.onChange(
                        number,
                        name,
                        expiration,
                        it
                    )
                },
                error = { validateCcv(it, cardType) },
                label = stringResource(R.string.ccv),
                modifier = Modifier.weight(0.5f)
            )
        }

    }
}

