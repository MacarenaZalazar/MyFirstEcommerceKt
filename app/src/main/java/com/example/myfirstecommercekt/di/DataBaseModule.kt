package com.example.myfirstecommercekt.di

import android.content.*
import androidx.room.*
import com.example.myfirstecommercekt.data.local.*
import com.example.myfirstecommercekt.data.local.dao.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.android.qualifiers.*
import dagger.hilt.components.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext, AppDatabase::class.java, "peya-database"
        ).fallbackToDestructiveMigration(true).build()

    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideCartItemDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }
}