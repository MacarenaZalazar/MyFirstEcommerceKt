package com.example.toramarket.ui.screens.products

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import com.example.toramarket.R

@Composable
fun CategoriesSelector(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    LazyRow(
        Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.padding_8)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_4))
    ) {
        item {
            Button(
                onClick = { onCategorySelected(null) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                val text =
                    if (selectedCategory == null) stringResource(R.string.todos) else stringResource(
                        R.string.quitar_filtro
                    )
                Text(text)
            }
        }
        items(categories) { category ->
            Button(
                onClick = { onCategorySelected(category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (category == selectedCategory) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                )
            ) {
                Text(category)
            }
        }

    }
}
