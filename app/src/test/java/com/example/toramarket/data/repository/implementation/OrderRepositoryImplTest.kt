package com.example.toramarket.data.repository.implementation

import com.example.toramarket.*
import com.example.toramarket.data.remote.api.*
import com.example.toramarket.data.remote.dto.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class OrderRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var service: OrderService
    private lateinit var repositorio: OrderRepositoryImpl

    @Before
    fun setUp() {
        service = mockk()
        repositorio = OrderRepositoryImpl(service)
    }

    @Test
    fun `getOrdersByUser retorna la lista de órdenes para un usuario`() = runTest() {
        val idUsuario = "user123"
        val ordenes = listOf(
            OrderDto(
                "order1", idUsuario,
                created = Date(),
                items = listOf(
                    OrderItemDto("item1", "product1", 2, 10.0),
                    OrderItemDto("item2", "product2", 1, 20.0)
                ),
                total = 40.00
            ),
        )
        coEvery { service.getOrdersByUser(idUsuario) } returns ordenes

        val resultado = repositorio.getOrdersByUser(idUsuario)

        assertEquals(ordenes, resultado)
    }

    @Test
    fun `createOrder crea una nueva orden y la retorna`() = runTest() {
        val request = NewOrderDto(
            user = "user123",
            items = listOf(
                NewOrderItemDto(productName = "product1", quantity = 2, price = 10.0),
                NewOrderItemDto(productName = "product2", quantity = 1, price = 20.0)
            ),
            total = 40.00
        )
        val ordenCreada = OrderDto(
            "order1", "user123", created = Date(),
            items = listOf(
                OrderItemDto("item1", "product1", 2, 10.0),
                OrderItemDto("item2", "product2", 1, 20.0)
            ),
            total = 40.00
        )
        coEvery { service.createOrder(request) } returns ordenCreada

        val resultado = repositorio.createOrder(request)

        assertEquals(ordenCreada, resultado)
    }
}