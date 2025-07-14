package com.example.toramarket.utils.data

import java.util.*

data class Order(val id: String, val items: List<OrderItem>, val created: Date)