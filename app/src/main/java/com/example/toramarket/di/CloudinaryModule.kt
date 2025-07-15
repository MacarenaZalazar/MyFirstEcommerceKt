package com.example.toramarket.di

import com.cloudinary.*
import dagger.*
import dagger.hilt.*
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
}