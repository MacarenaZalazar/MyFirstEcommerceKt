package com.example.toramarket.data.repository.interfaces

import android.net.*

interface ImageUploadRepository {
    suspend fun uploadImage(uri: Uri): String?
}