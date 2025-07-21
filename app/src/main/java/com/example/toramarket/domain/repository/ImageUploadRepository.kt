package com.example.toramarket.domain.repository

import android.net.*

interface ImageUploadRepository {
    suspend fun uploadImage(uri: Uri): String?
}