package com.example.toramarket.data.repository.implementation

import android.content.*
import android.net.*
import com.example.toramarket.data.remote.api.*
import com.example.toramarket.data.repository.interfaces.*
import javax.inject.*

class ImageUploadRepositoryImpl @Inject constructor(private val service: CloudinaryService) :
    ImageUploadRepository {
    override suspend fun uploadImage(
        uri: Uri,
        context: Context
    ): String? {
        return service.uploadImageToCloudinary(context, uri)
    }

}