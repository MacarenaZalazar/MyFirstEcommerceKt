package com.example.toramarket.utils.helpers

import com.example.toramarket.utils.data.*

fun getCategories(items: List<Product>) = items.map { it.category }