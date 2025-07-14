package com.example.toramarket.utils.helpers

import android.util.*

fun isValidPassword(value: String): Boolean {
    val regex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+=\\-{}\\[\\]:;\"'<>,.?/\\\\|`~]).{8,15}$")
    return regex.matches(value)
}

fun isValidEmail(value: String) = Patterns.EMAIL_ADDRESS.matcher(value).matches()

fun isValidName(value: String): Boolean {
    val repeatedCharsRegex = Regex("(.)\\1{2,}")
    val words = value.trim().split("\\s+".toRegex())
    return value.isNotBlank() && !repeatedCharsRegex.containsMatchIn(value) && (words.size > 1)
}

fun passwordError(value: String): String? {
    if (value.isBlank()) return "La contraseña no puede estar vacía"
    if (value.length < 8) return "Debe tener al menos 8 caracteres"
    if (!value.any { it.isUpperCase() }) return "Debe tener al menos una mayúscula"
    if (!value.any { it.isDigit() }) return "Debe tener al menos un número"
    if (!value.any { !it.isLetterOrDigit() }) return "Debe tener al menos un símbolo"
    return null
}

fun emailError(value: String): String? {
    if (value.isBlank()) return "El correo no puede estar vacío"
    if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) return "Correo inválido"
    return null
}

fun confirmPasswordError(password: String, confirm: String): String? {
    if (confirm.isBlank()) return "Confirmación requerida"
    if (password != confirm) return "Las contraseñas no coinciden"
    return null
}

fun validateName(value: String): String? {
    if (value.isBlank()) return "Este campo no puede estar vacío"
    val repeatedCharsRegex = Regex("(.)\\1{2,}")
    if (repeatedCharsRegex.containsMatchIn(value)) return "No puede tener más de 2 caracteres iguales seguidos"
    val words = value.trim().split("\\s+".toRegex())
    if (words.size < 2) "Debe ingresar al menos dos palabras"
    return null
}

fun isValidCard(card: String) {}

fun detectCardType(card: String) {

}
