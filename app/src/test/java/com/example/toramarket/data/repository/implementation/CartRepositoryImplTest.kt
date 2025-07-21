package com.example.toramarket.data.repository.implementation

import com.example.toramarket.*
import com.example.toramarket.data.local.dao.*
import com.example.toramarket.data.local.entity.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class CartRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val dao = mockk<CartDao>()
    private val repo = CartRepositoryImpl(dao)

    @Test
    fun `getCartItems retorna el flujo de items del carrito`() = runTest {
        val items = listOf(
            mockk<CartItemWithProduct>(),
            mockk<CartItemWithProduct>()
        )
        every { dao.getCartItems() } returns flowOf(items)

        val resultado = repo.getCartItems()

        val resultadoList = resultado.toList()

        Assert.assertEquals(listOf(items), resultadoList)
    }

    @Test
    fun `getCartItemById retorna el item correcto`() = runTest {
        val id = "item1"
        val item = mockk<CartItemWithProduct>()
        coEvery { dao.getCartItemById(id) } returns item

        val resultado = repo.getCartItemById(id)
        Assert.assertEquals(item, resultado)
    }

    @Test
    fun `insertCartItem llama a dao con el item correcto`() = runTest {
        val item = mockk<CartItemEntity>()
        coEvery { dao.insertCartItem(item) } returns Unit

        repo.insertCartItem(item)
        coVerify { dao.insertCartItem(item) }
    }

    @Test
    fun `updateCartItem llama a dao con el item correcto`() = runTest {
        val item = mockk<CartItemEntity>()
        coEvery { dao.updateCartItem(item) } returns Unit

        repo.updateCartItem(item)
        coVerify { dao.updateCartItem(item) }
    }

    @Test
    fun `deleteCartItem llama a dao con el item correcto`() = runTest {
        val item = mockk<CartItemEntity>()
        coEvery { dao.deleteCartItem(item) } returns Unit

        repo.deleteCartItem(item)
        coVerify { dao.deleteCartItem(item) }
    }

    @Test
    fun `clearCart llama a dao correctamente`() = runTest {
        coEvery { dao.clearCart() } returns Unit

        repo.clearCart()
        coVerify { dao.clearCart() }
    }
}