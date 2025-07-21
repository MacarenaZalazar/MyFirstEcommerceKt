import com.example.toramarket.*
import com.example.toramarket.data.local.entity.*
import com.example.toramarket.domain.cart.*
import com.example.toramarket.domain.orders.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.ui.*
import com.example.toramarket.ui.screens.checkout.*
import com.example.toramarket.utils.data.*
import com.example.toramarket.utils.helpers.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import java.io.*
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class CheckoutViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val clearCartUseCase: ClearCartUseCase = mockk()
    private val createOrderUseCase: CreateOrderUseCase = mockk()
    private val getUserIdUseCase: GetUserIdUseCase = mockk()
    private val getCartUseCase: GetCartUseCase = mockk()

    private val cartItemsFlow = MutableStateFlow<List<CartItemWithProduct>>(emptyList())

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        every { getCartUseCase.invoke() } returns cartItemsFlow
        viewModel = CheckoutViewModel(
            clearCartUseCase,
            createOrderUseCase,
            getUserIdUseCase,
            getCartUseCase
        )
    }

    @Test
    fun `getOrder actualiza uiState a Success si hay usuario y productos en el carrito`() =
        runTest {
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
            coEvery { getUserIdUseCase.invoke() } returns "user123"

            viewModel.getOrder()
            advanceUntilIdle()

            Assert.assertTrue(viewModel.uiState is UIState.Success)
            val order = (viewModel.uiState as UIState.Success<Order>).data
            Assert.assertEquals("user123", order.userId)
            Assert.assertEquals(150.0, order.total, 0.001)
            Assert.assertEquals(2, order.items.size)
        }

    @Test
    fun `getOrder pone Error si no hay usuario`() = runTest {
        coEvery { getUserIdUseCase.invoke() } returns null

        viewModel.getOrder()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals("Usuario no encontrado", (viewModel.uiState as UIState.Error).message)
    }

    @Test
    fun `getOrder pone Error si el carrito está vacío`() = runTest {
        coEvery { getUserIdUseCase.invoke() } returns "user123"
        cartItemsFlow.value = emptyList()

        viewModel.getOrder()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals("El carrito está vacío", (viewModel.uiState as UIState.Error).message)
    }

    @Test
    fun `getOrder pone Error en IOException`() = runTest {
        coEvery { getUserIdUseCase.invoke() } throws IOException()

        viewModel.getOrder()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals("Sin conexión a internet", (viewModel.uiState as UIState.Error).message)
    }

    @Test
    fun `changePaymentMethod actualiza el método y la validez`() = runTest {
        viewModel.changePaymentMethod(PaymentMethod.CASH)
        Assert.assertEquals(PaymentMethod.CASH, viewModel.paymentMethod.value)
        Assert.assertTrue(viewModel.isValid.value)

        viewModel.changePaymentMethod(PaymentMethod.CARD)
        Assert.assertEquals(PaymentMethod.CARD, viewModel.paymentMethod.value)
        Assert.assertFalse(viewModel.isValid.value)
    }

    @Test
    fun `onChange actualiza los campos y la validez correctamente`() {
        // Aquí deberías adaptar según tu lógica de validación
        viewModel.onChange("4111111111111111", "John Doe", "12/25", "123")
        Assert.assertEquals("4111111111111111", viewModel.number.value)
        Assert.assertEquals("John Doe", viewModel.name.value)
        Assert.assertEquals("12/25", viewModel.expiration.value)
        Assert.assertEquals("123", viewModel.cvv.value)

        Assert.assertTrue(viewModel.isValid.value)
    }

    @Test
    fun `closeDialog esconde el diálogo`() {
        viewModel.closeDialog()
        Assert.assertFalse(viewModel.showDialog.value)
    }

    @Test
    fun `pay crea la orden, limpia el carrito y muestra diálogo de éxito`() = runTest {
        val order = Order(
            id = "1",
            userId = "user123",
            items = listOf(),
            created = Date(),
            total = 100.0
        )
        viewModel.uiState = UIState.Success(order)
        viewModel.run {
            // Para que _order.value != null
            val field = this.javaClass.getDeclaredField("_order")
            field.isAccessible = true
            (field.get(this) as MutableStateFlow<Order?>).value = order
        }

        coEvery { createOrderUseCase.invoke(any()) } returns order
        coEvery { clearCartUseCase.invoke() } just Runs

        viewModel.pay()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.showDialog.value)
        Assert.assertEquals("Tu compra ha sido realizada con éxito", viewModel.dialogMessage.value)
        Assert.assertTrue(viewModel.uiState is UIState.Success)
    }

    @Test
    fun `pay pone Error en IOException`() = runTest {
        val order = Order(
            id = "1",
            userId = "user123",
            items = listOf(),
            created = Date(),
            total = 100.0
        )
        viewModel.run {
            val field = this.javaClass.getDeclaredField("_order")
            field.isAccessible = true
            (field.get(this) as MutableStateFlow<Order?>).value = order
        }
        coEvery { createOrderUseCase.invoke(order) } throws IOException()

        viewModel.pay()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals("Sin conexión a internet", (viewModel.uiState as UIState.Error).message)
    }


}