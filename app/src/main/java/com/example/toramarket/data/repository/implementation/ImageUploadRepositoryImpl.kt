package com.example.toramarket.data.repository.implementation

import android.net.*
import com.example.toramarket.data.remote.api.*
import com.example.toramarket.domain.repository.*
import javax.inject.*

class ImageUploadRepositoryImpl @Inject constructor(private val service: CloudinaryService) :
    ImageUploadRepository {
    override suspend fun uploadImage(
        uri: Uri
    ): String? {
        return service.uploadImage(uri)
    }

}