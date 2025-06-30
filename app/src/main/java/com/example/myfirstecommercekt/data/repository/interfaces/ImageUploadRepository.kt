package com.example.myfirstecommercekt.data.repository.interfaces

import android.content.*
import android.net.*

interface ImageUploadRepository {
    suspend fun uploadImage(uri: Uri, context: Context): String?
}