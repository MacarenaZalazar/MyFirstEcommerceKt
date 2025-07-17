package com.example.toramarket.data.local.entity

import androidx.room.*
import com.example.toramarket.utils.data.*

data class CartItemWithProduct(
    @Embedded val cartItem: CartItemEntity,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: ProductEntity

) {
    fun toOrderItem() = OrderItem(
        id = "",
        productName = product.name,
        quantity = cartItem.quantity,
        price = product.price
    )
}
