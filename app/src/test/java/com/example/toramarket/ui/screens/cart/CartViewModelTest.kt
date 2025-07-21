import com.example.toramarket.*
import com.example.toramarket.data.local.entity.*
import com.example.toramarket.domain.cart.*
import com.example.toramarket.ui.screens.cart.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mocks de los use cases
    private val getCartUseCase: GetCartUseCase = mockk()
    private val addToCartUseCase: AddToCartUseCase = mockk()
    private val removeFromCartUseCase: RemoveFromCartUseCase = mockk()
    private val clearCartUseCase: ClearCartUseCase = mockk()

    private val cartItemsFlow = MutableStateFlow<List<CartItemWithProduct>>(emptyList())

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        every { getCartUseCase.invoke() } returns cartItemsFlow
        viewModel = CartViewModel(
            getCartUseCase,
            addToCartUseCase,
            removeFromCartUseCase,
            clearCartUseCase
        )
    }

    @Test
    fun `init actualiza los totales correctamente si hay productos`() = runTest {
        val item1 =
            CartItemWithProduct(
                product = ProductEntity(
                    id = "1",
                    name = "Tacos al pastor",
                    description = "Tortillas con carne de cerdo marinada, piña y cebolla.",
                    imageUrl = "https://comedera.com/wp-content/uploads/sites/9/2017/08/tacos-al-pastor-receta.jpg",
                    price = 45.0,
                    category = "Comida mexicana"
                ), cartItem = CartItemEntity(1, "1", 2)
            )
        val item2 =
            CartItemWithProduct(
                product = ProductEntity(
                    id = "2",
                    name = "Burrito de res",
                    description = "Con arroz, frijoles, carne de res y queso.",
                    imageUrl = "https://www.recetasnestle.com.mx/sites/default/files/srh_recipes/cff9881a271d21ae3d098ba68d5ecd18.jpg",
                    price = 60.0,
                    category = "Comida mexicana"
                ),
                cartItem = CartItemEntity(2, "2", 1)
            )
        cartItemsFlow.value = listOf(item1, item2)

        advanceUntilIdle()

        viewModel.updateTotals()

        Assert.assertEquals(150.0, viewModel.subtotal.value, 0.001)
        Assert.assertEquals(3, viewModel.count.value)
    }

    @Test
    fun `addToCart invoca usecase y emite mensaje de éxito`() = runTest {
        coEvery { addToCartUseCase.invoke("1") } just Runs

        val messages = mutableListOf<String>()
        val job = launch {
            viewModel.snackbarMessage.collect { messages.add(it) }
        }

        viewModel.addToCart("1")
        advanceUntilIdle()

        coVerify { addToCartUseCase.invoke("1") }
        Assert.assertTrue(messages.any { it == "Producto agregado al carrito" })

        job.cancel()
    }

    @Test
    fun `addToCart emite mensaje de error si falla el usecase`() = runTest {
        coEvery { addToCartUseCase.invoke("1") } throws Exception("fallo")

        val messages = mutableListOf<String>()
        val job = launch {
            viewModel.snackbarMessage.collect { messages.add(it) }
        }

        viewModel.addToCart("1")
        advanceUntilIdle()

        Assert.assertTrue(messages.any { it.contains("Error al agregar al carrito: fallo") })

        job.cancel()
    }

    @Test
    fun `removeFromCart invoca usecase y emite mensaje de éxito`() = runTest {
        coEvery { removeFromCartUseCase.invoke("1") } just Runs

        val messages = mutableListOf<String>()
        val job = launch {
            viewModel.snackbarMessage.collect { messages.add(it) }
        }

        viewModel.removeFromCart("1")
        advanceUntilIdle()

        coVerify { removeFromCartUseCase.invoke("1") }
        Assert.assertTrue(messages.any { it == "Producto eliminado del carrito" })

        job.cancel()
    }

    @Test
    fun `removeFromCart emite mensaje de error si falla el usecase`() = runTest {
        coEvery { removeFromCartUseCase.invoke("1") } throws Exception("fallo")

        val messages = mutableListOf<String>()
        val job = launch {
            viewModel.snackbarMessage.collect { messages.add(it) }
        }

        viewModel.removeFromCart("1")
        advanceUntilIdle()

        Assert.assertTrue(messages.any { it.contains("Error al eliminar del carrito: fallo") })

        job.cancel()
    }

    @Test
    fun `clearCart invoca usecase`() = runTest {
        coEvery { clearCartUseCase.invoke() } just Runs

        viewModel.clearCart()
        advanceUntilIdle()

        coVerify { clearCartUseCase.invoke() }
    }

    @Test
    fun `updateTotals suma correctamente subtotal y cantidad`() = runTest {
        val item1 =
            CartItemWithProduct(
                product = ProductEntity(
                    id = "1",
                    name = "Tacos al pastor",
                    description = "Tortillas con carne de cerdo marinada, piña y cebolla.",
                    imageUrl = "https://comedera.com/wp-content/uploads/sites/9/2017/08/tacos-al-pastor-receta.jpg",
                    price = 45.0,
                    category = "Comida mexicana"
                ), cartItem = CartItemEntity(1, "1", 2)
            )
        val item2 =
            CartItemWithProduct(
                product = ProductEntity(
                    id = "2",
                    name = "Burrito de res",
                    description = "Con arroz, frijoles, carne de res y queso.",
                    imageUrl = "https://www.recetasnestle.com.mx/sites/default/files/srh_recipes/cff9881a271d21ae3d098ba68d5ecd18.jpg",
                    price = 60.0,
                    category = "Comida mexicana"
                ),
                cartItem = CartItemEntity(2, "2", 1)
            )
        cartItemsFlow.value = listOf(item1, item2)
        advanceUntilIdle()

        viewModel.updateTotals()

        Assert.assertEquals(150.0, viewModel.subtotal.value, 0.001)
        Assert.assertEquals(3, viewModel.count.value)
    }
}