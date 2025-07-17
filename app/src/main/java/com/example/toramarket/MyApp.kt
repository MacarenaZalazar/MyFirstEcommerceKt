package com.example.toramarket

import android.app.*
import dagger.hilt.android.*
import javax.inject.*

@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var workManagerHelper: WorkManagerHelper
    override fun onCreate() {
        super.onCreate()
        workManagerHelper.startProductSync()
    }


}