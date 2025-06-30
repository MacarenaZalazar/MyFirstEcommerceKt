package com.example.myfirstecommercekt.data.remote.dto

data class AuthRequest(val email: String, val encryptedPassword: String)
data class ProductDto(
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageUrl: String
)