package com.example.myfirstecommercekt.ui.screens.products

import com.example.myfirstecommercekt.*
import com.example.myfirstecommercekt.data.repository.interfaces.*
import io.mockk.*
import kotlinx.coroutines.*
import org.junit.*

@ExperimentalCoroutinesApi
class ProductsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    val repository: ProductRepository = mockk()

    @Test

    @Before
    fun setup() {
        coEvery { }
    }
//    @After

}