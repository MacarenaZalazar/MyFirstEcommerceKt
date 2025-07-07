package com.example.myfirstecommercekt

import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.rules.*
import org.junit.runner.*

@ExperimentalCoroutinesApi
class MainDispatcherRule
    (val testDispatcher: TestDispatcher = StandardTestDispatcher()) : TestWatcher() {
    override fun starting(description: Description?) {
        Dispatchers.setMain(dispatcher = testDispatcher)
    }

    override fun finished(description: org.junit.runner.Description?) {
        Dispatchers.resetMain()
    }
}