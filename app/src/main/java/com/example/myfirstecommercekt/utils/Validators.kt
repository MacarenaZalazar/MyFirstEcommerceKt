package com.example.myfirstecommercekt.utils

import android.util.Patterns

fun isValidPassword(value: String): Boolean {
    val regex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|`~]).{8,15}$")
    return regex.matches(value)
}

fun isValidEmail(value: String) = Patterns.EMAIL_ADDRESS.matcher(value).matches()

