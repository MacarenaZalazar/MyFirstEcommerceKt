package com.example.toramarket

import android.content.*
import androidx.work.*
import dagger.hilt.android.qualifiers.*
import javax.inject.*


@Singleton
class WorkManagerHelper @Inject constructor(@ApplicationContext private val context: Context) {
    val workManager = WorkManager.getInstance(context)
    fun startProductSync() {
        val workRequest = PeriodicWorkRequestBuilder<ProductSyncManager>(
            15,
            java.util.concurrent.TimeUnit.MINUTES
        )
            .build()
        workManager.enqueueUniquePeriodicWork(
            "ProductSyncWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}