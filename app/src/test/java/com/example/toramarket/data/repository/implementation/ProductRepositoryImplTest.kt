package com.example.toramarket.data.repository.implementation

import com.example.toramarket.*
import com.example.toramarket.data.local.dao.*
import com.example.toramarket.data.local.entity.*
import com.example.toramarket.data.remote.api.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.utils.data.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class ProductRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var local: ProductDao
    private lateinit var remote: ProductsService
    private lateinit var repositorio: ProductRepositoryImpl

    @Before
    fun setUp() {
        local = mockk(relaxed = true)
        remote = mockk()
        repositorio = ProductRepositoryImpl(local, remote)
    }

    @Test
    fun `getAll con refresh true obtiene de remoto, borra y actualiza local, y retorna los productos del dominio`() =
        runTest() {
            val productosRemotos = listOf(
                ProductDto("id1", "Producto 1", "description", 10.0, "categoria", "url"),
                ProductDto("id2", "Producto 2", "description", 20.0, "categoria", "url")
            )

            coEvery { remote.getProducts() } returns productosRemotos
            coEvery { local.clearProducts() } returns Unit
            coEvery { local.insertAll(any()) } returns Unit

            val resultado = repositorio.getAll(refresh = true)

            // Verifica que borra productos, inserta y retorna correctamente
            coVerify { local.clearProducts() }
            coVerify { local.insertAll(productosRemotos.map { it.toEntity() }) }
            assertEquals(productosRemotos.map { it.toDomain() }, resultado)
        }

    @Test
    fun `getAll con refresh false y productos locales retorna los productos locales del dominio`() =
        runTest() {
            val productoEntity1 = mockk<ProductEntity>()
            val productoEntity2 = mockk<ProductEntity>()
            val productoDomain1 =
                Product("id1", "Producto 1", "description", 10.0, "categoria", "url")
            val productoDomain2 =
                Product("id2", "Producto 2", "description", 20.0, "categoria", "url")

            coEvery { productoEntity1.toDomain() } returns productoDomain1
            coEvery { productoEntity2.toDomain() } returns productoDomain2

            coEvery { local.getAll() } returns listOf(productoEntity1, productoEntity2)

            val resultado = repositorio.getAll(refresh = false)

            coVerify { local.getAll() }
            assertEquals(listOf(productoDomain1, productoDomain2), resultado)
        }

    @Test
    fun `getAll con refresh false y sin productos locales obtiene de remoto, actualiza local y retorna los productos del dominio`() =
        runTest() {
            coEvery { local.getAll() } returns emptyList()
            val productosRemotos = listOf(
                ProductDto("id1", "Producto 1", "description", 10.0, "categoria", "url"),
                ProductDto("id2", "Producto 2", "description", 20.0, "categoria", "url")
            )
            coEvery { remote.getProducts() } returns productosRemotos
            coEvery { local.insertAll(any()) } returns Unit

            val resultado = repositorio.getAll(refresh = false)

            coVerify { remote.getProducts() }
            coVerify { local.insertAll(productosRemotos.map { it.toEntity() }) }
            assertEquals(productosRemotos.map { it.toDomain() }, resultado)
        }
}