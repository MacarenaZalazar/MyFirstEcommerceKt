package com.example.myfirstecommercekt.utils.helpers

import android.util.*

fun isValidPassword(value: String): Boolean {
    val regex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|`~]).{8,15}$")
    return regex.matches(value)
}

fun isValidEmail(value: String) = Patterns.EMAIL_ADDRESS.matcher(value).matches()

fun isValidCard(card: String) {}

fun detectCardType(card: String) {

}
