package com.example.toramarket

import android.content.*
import androidx.work.*
import com.example.toramarket.domain.products.*
import dagger.hilt.android.*
import kotlinx.coroutines.*

class ProductSyncManager(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): ListenableWorker.Result {
        // Accede a GetProductsUseCase usando Hilt EntryPoint
        val entryPoint = EntryPointAccessors.fromApplication(
            applicationContext,
            GetProductsUseCaseEntryPoint::class.java
        )
        val useCase = entryPoint.getProductsUseCase()
        // Ejecuta la llamada (debe ser bloqueante)
        runBlocking {
            useCase.invoke(refresh = true)
        }
        return ListenableWorker.Result.success()
    }
}

@dagger.hilt.EntryPoint
@dagger.hilt.InstallIn(dagger.hilt.components.SingletonComponent::class)
interface GetProductsUseCaseEntryPoint {
    fun getProductsUseCase(): GetProductsUseCase
}