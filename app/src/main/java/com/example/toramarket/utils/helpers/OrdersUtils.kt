package com.example.toramarket.utils.helpers

import java.time.*
import java.time.format.*
import java.util.*

fun formatDate(date: Date): String {
    val zoned = date.toInstant().atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("EEE d 'de' MMMM, HH:mm", Locale("es", "ES"))
    val raw = zoned.format(formatter)
    return raw.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale(
                "es",
                "ES"
            )
        ) else it.toString()
    }
}