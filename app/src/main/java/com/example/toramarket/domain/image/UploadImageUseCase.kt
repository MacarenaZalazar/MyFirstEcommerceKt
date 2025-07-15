package com.example.toramarket.domain.image

import android.net.*
import com.example.toramarket.data.repository.interfaces.*
import javax.inject.*

class UploadImageUseCase @Inject constructor(private val repository: ImageUploadRepository) {
    suspend operator fun invoke(uri: Uri): String? = repository.uploadImage(uri)
}