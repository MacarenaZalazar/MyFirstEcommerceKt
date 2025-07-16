package com.example.toramarket.di

import android.app.*
import android.content.*
import com.cloudinary.*
import com.example.toramarket.data.remote.api.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
class CloudinaryModule {
    val apikey = ""
    val apiSecret = ""
    val cloudName = ""

    private val cloudinary = Cloudinary(
        mapOf(
            "cloud_name" to cloudName,
            "api_key" to apikey,
            "api_secret" to apiSecret
        )
    )

    @Provides
    @Singleton
    fun provideCloudinary(): Cloudinary {
        return cloudinary
    }

    @Provides
    fun provideCloudinaryService(
        cloudinary: Cloudinary,
        @ApplicationContext context: Context
    ): CloudinaryService {
        return CloudinaryService(cloudinary, context.applicationContext as Application)
    }
}