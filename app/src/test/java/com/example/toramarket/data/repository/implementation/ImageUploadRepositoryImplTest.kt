package com.example.toramarket.data.repository.implementation

import android.net.*
import com.example.toramarket.*
import com.example.toramarket.data.remote.api.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ImageUploadRepositoryImplTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var service: CloudinaryService
    private lateinit var repositorio: ImageUploadRepositoryImpl

    @Before
    fun setUp() {
        service = mockk()
        repositorio = ImageUploadRepositoryImpl(service)
    }

    @Test
    fun `uploadImage retorna la url cuando el servicio responde correctamente`() =
        runTest {
            val uri = mockk<Uri>()
            val urlEsperada = "https://ejemplo.com/imagen.jpg"
            coEvery { service.uploadImage(uri) } returns urlEsperada

            val resultado = repositorio.uploadImage(uri)

            assertEquals(urlEsperada, resultado)
        }

}