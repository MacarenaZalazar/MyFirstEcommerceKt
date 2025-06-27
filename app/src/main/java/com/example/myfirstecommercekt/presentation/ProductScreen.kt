package com.example.myfirstecommercekt.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProductScreen() {
    val products = listOf<String>("bananas", "manzanas", "peras ")

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Listado de productos")
        ProductList()
    }
}

@Composable

fun ProductList() {
    LazyColumn() {  }
}
