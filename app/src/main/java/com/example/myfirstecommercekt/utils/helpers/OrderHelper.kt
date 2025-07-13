package com.example.myfirstecommercekt.utils.helpers

import com.example.myfirstecommercekt.utils.data.*

fun totalAmount(items: List<OrderItem>) = items.sumOf {
    it.price * it.quantity
}

fun totalCount(items: List<OrderItem>) = items.sumOf { it.quantity }