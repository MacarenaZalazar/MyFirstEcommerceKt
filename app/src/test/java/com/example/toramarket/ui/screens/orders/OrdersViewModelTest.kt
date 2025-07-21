package com.example.toramarket.ui.screens.orders

import android.net.http.*
import com.example.toramarket.*
import com.example.toramarket.domain.orders.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.ui.*
import com.example.toramarket.utils.data.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import java.io.*
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class OrdersViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getOrdersUseCase: GetOrdersUseCase = mockk()
    private val getUserIdUseCase: GetUserIdUseCase = mockk()
    private lateinit var viewModel: OrdersViewModel

    @Before
    fun setUp() {
        viewModel = OrdersViewModel(getOrdersUseCase, getUserIdUseCase)
    }

    @Test
    fun `loadOrders actualiza uiState con éxito si usuario encontrado`() = runTest {
        val userId = "123"
        val orders = listOf(
            Order(
                userId = "1",
                items = listOf(
                    OrderItem(
                        productName = "prod1",
                        quantity = 2,
                        price = 10.0,
                        id = "1"
                    )
                ),
                created = Date(),
                id = "order1",
                total = 100.00
            )
        )


        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { getOrdersUseCase.invoke(userId) } returns orders

        viewModel.loadOrders()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Success)
        Assert.assertEquals(orders, (viewModel.uiState as UIState.Success<List<Order>>).data)
    }

    @Test
    fun `loadOrders muestra error si usuario no encontrado`() = runTest {
        coEvery { getUserIdUseCase.invoke() } returns null

        viewModel.loadOrders()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals("Usuario no encontrado", (viewModel.uiState as UIState.Error).message)
    }

    @Test
    fun `loadOrders muestra error de red si IOException`() = runTest {
        val userId = "123"
        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { getOrdersUseCase.invoke(userId) } throws IOException("network error")

        viewModel.loadOrders()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals("Sin conexión a internet", (viewModel.uiState as UIState.Error).message)
    }

    @Test
    fun `loadOrders muestra error de servidor si HttpException`() = runTest {
        val httpException = mockk<HttpException> {
            every { message } returns "Error 500"
        }
        val userId = "123"
        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { getOrdersUseCase.invoke(any()) } throws httpException

        viewModel.loadOrders()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
    }

    @Test
    fun `loadOrders muestra error inesperado si Exception`() = runTest {
        val userId = "123"
        coEvery { getUserIdUseCase.invoke() } returns userId
        coEvery { getOrdersUseCase.invoke(userId) } throws Exception("random error")

        viewModel.loadOrders()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertTrue((viewModel.uiState as UIState.Error).message.startsWith("Ocurrió un error inesperado"))
    }
}