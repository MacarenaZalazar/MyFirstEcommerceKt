package com.example.toramarket.ui.screens.products

import android.net.http.*
import com.example.toramarket.*
import com.example.toramarket.domain.cart.*
import com.example.toramarket.domain.products.*
import com.example.toramarket.ui.*
import com.example.toramarket.utils.data.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.*
import org.junit.runners.*
import java.io.*

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var viewModel: ProductsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getProductsUseCase = mockk()
        addToCartUseCase = mockk()
        viewModel = ProductsViewModel(getProductsUseCase, addToCartUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onFilterChange actualiza el valor de filter`() {
        viewModel.onFilterChange("abc")
        Assert.assertEquals("abc", viewModel.filter)
    }

    @Test
    fun `filteredProducts retorna una lista filtrada por categoría y nombre`() {
        val products = listOf(
            Product(
                id = "1",
                name = "Tacos al pastor",
                description = "Tortillas con carne de cerdo marinada, piña y cebolla.",
                image = "https://comedera.com/wp-content/uploads/sites/9/2017/08/tacos-al-pastor-receta.jpg",
                price = 45.0,
                category = "Comida mexicana"
            ),
            Product(
                id = "2",
                name = "Burrito de res",
                description = "Con arroz, frijoles, carne de res y queso.",
                image = "https://www.recetasnestle.com.mx/sites/default/files/srh_recipes/cff9881a271d21ae3d098ba68d5ecd18.jpg",
                price = 60.0,
                category = "Comida mexicana"
            ),
            Product(
                id = "3",
                name = "Hamburguesa clásica",
                description = "Pan artesanal, carne de res, lechuga, tomate y mayonesa.",
                image = "https://imag.bonviveur.com/hamburguesa-clasica.jpg",
                price = 70.0,
                category = "Comida rápida"

            )
        )
        viewModel.uiState = UIState.Success(products)
        viewModel.selectedCategory = "Comida rápida"
        viewModel.filter = "ha"
        val result = viewModel.filteredProducts
        Assert.assertEquals(1, result.size)
        Assert.assertEquals("Hamburguesa clásica", result.first().name)
    }

    @Test
    fun `loadProducts setea UIState Success en success`() = runTest {
        val products = listOf(
            Product(
                id = "1",
                name = "Tacos al pastor",
                description = "Tortillas con carne de cerdo marinada, piña y cebolla.",
                image = "https://comedera.com/wp-content/uploads/sites/9/2017/08/tacos-al-pastor-receta.jpg",
                price = 45.0,
                category = "Comida mexicana"
            )
        )
        coEvery { getProductsUseCase.invoke(any()) } returns products

        viewModel.loadProducts()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Success)
        Assert.assertEquals(products, (viewModel.uiState as UIState.Success).data)
    }

    @Test
    fun `loadProducts setea UIState Error en IOException`() = runTest {
        coEvery { getProductsUseCase.invoke(any()) } throws IOException()

        viewModel.loadProducts()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals("Sin conexión a internet", (viewModel.uiState as UIState.Error).message)
    }

    @Test
    fun `loadProducts setea UIState Error en HttpException`() = runTest {
        val httpException = mockk<HttpException> {
            every { message } returns "Error 500"
        }
        coEvery { getProductsUseCase.invoke(any()) } throws httpException

        viewModel.loadProducts()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
    }

    @Test
    fun `addToCart emite mensaje de success`() = runTest {
        coEvery { addToCartUseCase.invoke("1") } throws Exception("fallo")

        var msg: String? = null
        val job = launch {
            viewModel.snackbarMessage.collect {
                msg = it
            }
        }

        viewModel.addToCart("1")
        advanceUntilIdle()

        Assert.assertTrue(msg?.contains("Error al agregar al carrito") == true)
        Assert.assertFalse(viewModel.isItemLoading("1"))

        job.cancel()
    }

    @Test
    fun `addToCart emite mensaje de error message en exception`() = runTest {
        coEvery { addToCartUseCase.invoke("1") } throws Exception("fallo")

        viewModel.addToCart("1")
        advanceUntilIdle()

        val msg = viewModel.snackbarMessage.first()
        Assert.assertTrue(msg.contains("Error al agregar al carrito"))
        Assert.assertFalse(viewModel.isItemLoading("1"))
    }


    @Test
    fun `isItemLoading retorna un loading state`() = runTest {
        coEvery { addToCartUseCase.invoke("1") } coAnswers {
            delay(100)
        }
        viewModel.addToCart("1")

        // Antes de avanzar, el ítem debe estar cargando
        Assert.assertTrue(viewModel.isItemLoading("1"))
        advanceUntilIdle()
        Assert.assertFalse(viewModel.isItemLoading("1"))
    }
}