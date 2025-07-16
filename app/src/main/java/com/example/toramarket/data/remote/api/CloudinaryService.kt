package com.example.toramarket.data.remote.api

import android.app.*
import android.net.*
import com.cloudinary.*
import javax.inject.*

private const val SECURE_URL_KEY = "secure_url"
private const val UPLOAD_PRESET_KEY = "upload_preset"
private const val UPLOAD_PRESET_VALUE = "ProfileImage"

class CloudinaryService @Inject constructor(
    private val cloudinary: Cloudinary,
    private val application: Application
) {

    suspend fun uploadImage(uri: Uri): String {
        val inputStream = application.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Error al cargar la imagen $uri")

        val options = mapOf(UPLOAD_PRESET_KEY to UPLOAD_PRESET_VALUE)
        val result = cloudinary.uploader().upload(inputStream, options)
        return result[SECURE_URL_KEY] as String
    }
}