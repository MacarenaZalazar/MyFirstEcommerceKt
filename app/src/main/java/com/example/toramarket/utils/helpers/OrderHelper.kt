package com.example.toramarket.utils.helpers

import com.example.toramarket.utils.data.*

fun totalAmount(items: List<OrderItem>) = items.sumOf {
    it.price * it.quantity
}

fun totalCount(items: List<OrderItem>) = items.sumOf { it.quantity }