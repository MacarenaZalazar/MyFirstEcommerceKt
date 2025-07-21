package com.example.toramarket.utils.helpers

import java.time.*
import java.time.format.*
import java.util.*

enum class CardType {
    VISA, MASTERCARD, AMEX, DISCOVER, UNKNOWN
}

fun isValidCard(card: String): Boolean {
    val digits = card.filter { it.isDigit() }
    if (digits.length !in 13..19) return false

    val checksum = digits
        .reversed()
        .mapIndexed { index, c ->
            val digit = c.toString().toInt()
            if (index % 2 == 1) {
                val doubled = digit * 2
                if (doubled > 9) doubled - 9 else doubled
            } else digit
        }.sum()

    return checksum % 10 == 0
}


fun detectCardType(card: String): CardType {
    val sanitized = card.filter { it.isDigit() }
    return when {
        sanitized.startsWith("4") -> CardType.VISA
        sanitized.matches(Regex("^5[1-5].*")) -> CardType.MASTERCARD
        sanitized.matches(Regex("^3[47].*")) -> CardType.AMEX
        sanitized.matches(Regex("^6(?:011|5).*")) -> CardType.DISCOVER
        else -> CardType.UNKNOWN
    }
}

fun isValidCVV(cvv: String, cardType: CardType): Boolean {
    val digitsOnly = cvv.filter { it.isDigit() }
    return when (cardType) {
        CardType.AMEX -> digitsOnly.length == 4
        CardType.VISA, CardType.MASTERCARD, CardType.DISCOVER -> digitsOnly.length == 3
        else -> false
    }
}

fun isValidExpiryDate(expiry: String): Boolean {
    val formatter = when {
        expiry.matches(Regex("""\d{2}/\d{2}""")) -> DateTimeFormatter.ofPattern("MM/yy")
        expiry.matches(Regex("""\d{2}/\d{4}""")) -> DateTimeFormatter.ofPattern("MM/yyyy")
        else -> return false
    }

    return try {
        val inputDate = YearMonth.parse(expiry, formatter)
        val currentDate = YearMonth.now()
        inputDate >= currentDate
    } catch (e: DateTimeParseException) {
        false
    }
}

fun formatExpiryInput(input: String): String {
    val digits = input.filter { it.isDigit() }.take(4)
    if (digits.isEmpty()) return ""
    if (digits.length < 4) return digits

    var mm = digits.substring(0, 2)
    val yy = digits.substring(2, 4)

    if (mm.length == 1) mm = "0$mm"

    val monthInt = mm.toIntOrNull()
    return if (monthInt != null && monthInt in 1..12) "$mm/$yy" else digits
}

fun validateCardNumber(number: String): String? {
    val clean = number.filter { it.isDigit() }
    val type = detectCardType(clean)

    return when {
        clean.length < 13 -> "Número demasiado corto"
        clean.length > 19 -> "Número demasiado largo"
        type == CardType.UNKNOWN -> "Tipo de tarjeta no reconocido"
        else -> null
    }
}

fun validateExpiration(exp: String): String? {
    if (!Regex("""^(0[1-9]|1[0-2])\/\d{2}$""").matches(exp)) return "Formato inválido (MM/YY)"

    val (monthStr, yearStr) = exp.split("/")
    val month = monthStr.toIntOrNull() ?: return "Mes inválido"
    val year = yearStr.toIntOrNull()?.plus(2000) ?: return "Año inválido"

    val now = Calendar.getInstance()
    val currentMonth = now.get(Calendar.MONTH) + 1
    val currentYear = now.get(Calendar.YEAR)

    return if (year < currentYear || (year == currentYear && month < currentMonth))
        "Tarjeta expirada"
    else null
}

fun validateCcv(ccv: String, cardType: CardType): String? {
    val clean = ccv.filter { it.isDigit() }
    val expected = when (cardType) {
        CardType.AMEX -> 4
        CardType.VISA, CardType.MASTERCARD, CardType.DISCOVER -> 3
        else -> 3
    }

    return if (clean.length != expected)
        "CCV debe tener $expected dígitos"
    else null
}
