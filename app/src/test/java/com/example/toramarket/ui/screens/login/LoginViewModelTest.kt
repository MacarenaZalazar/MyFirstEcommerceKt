package com.example.toramarket.ui.screens.login

import com.example.toramarket.*
import com.example.toramarket.data.local.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.domain.auth.*
import com.example.toramarket.utils.helpers.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import retrofit2.*

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userDataStore: UserDataStore = mockk(relaxed = true)
    private val authenticateUserUseCase: AuthenticateUserUseCase = mockk()


    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(
            userDataStore,
            authenticateUserUseCase
        )
        mockkStatic("com.example.toramarket.utils.helpers.UserFormUtilsKt")
    }

    @Test
    fun `onLoginChange actualiza email y password y habilita login si son válidos`() {
        every { isValidEmail(any()) } returns true

        viewModel.onLoginChange("test@email.com", "Hola123!")

        Assert.assertEquals("test@email.com", viewModel.email.value)
        Assert.assertEquals("Hola123!", viewModel.password.value)
        Assert.assertTrue(viewModel.loginEnabled.value)
    }

    @Test
    fun `onLoginChange deshabilita login si email no es válido`() {
        every { isValidEmail(any()) } returns false
        viewModel.onLoginChange("invalid-email", "12345678abc")
        Assert.assertFalse(viewModel.loginEnabled.value)
    }

    @Test
    fun `onLoginChange no actualiza password si excede 15 caracteres`() {
        viewModel.onLoginChange("test@email.com", "1234567890123456")
        Assert.assertEquals("", viewModel.password.value)
    }

    @Test
    fun `logIn exitoso guarda usuario y llama toHome`() = runTest {
        every { isValidEmail(any()) } returns true
        val userDto =
            UserDto(
                email = "test@email.com", fullName = "Test User", encryptedPassword = "",
                userImageUrl = ""
            )

        val loginResponse = LoginResponse(
            user = LoggerUserDto(
                id = "1", email = userDto.email, fullName = userDto.fullName,
                encryptedPassword = userDto.encryptedPassword, userImageUrl = userDto.userImageUrl
            ),
            message = "login exitoso"
        )
        val response: Response<LoginResponse> = Response.success(loginResponse)

        coEvery { authenticateUserUseCase.invoke(any(), any()) } returns response

        var toHomeCalled = false
        val toHome = { toHomeCalled = true }

        viewModel.onLoginChange("test@email.com", "mypassword")
        viewModel.logIn(toHome)
        advanceUntilIdle()

        coVerify {
            authenticateUserUseCase.invoke("test@email.com", any())
            userDataStore.saveUser(id = "1", name = "Test User", email = "test@email.com")
        }
        Assert.assertTrue(viewModel.success.value)
        Assert.assertTrue(toHomeCalled)
    }

    @Test
    fun `logIn error pone loading en false`() = runTest {
        every { isValidEmail(any()) } returns true

        coEvery { authenticateUserUseCase.invoke(any(), any()) } throws Exception("login fail")

        val toHome = mockk<() -> Unit>(relaxed = true)
        viewModel.onLoginChange("test@email.com", "mypassword")
        viewModel.logIn(toHome)
        advanceUntilIdle()

        Assert.assertFalse(viewModel.isLoading.value)
    }
}