package com.example.toramarket.profile

import com.example.toramarket.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.domain.image.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.ui.*
import com.example.toramarket.ui.screens.profile.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import retrofit2.*
import java.io.*

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private val getUserByEmailUseCase: GetUserByEmailUseCase = mockk()
    private val updateUserNameUseCase: UpdateUserNameUseCase = mockk()
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase = mockk()
    private val updateUserImgUseCase: UpdateUserImgUseCase = mockk()
    private val uploadImageUseCase: UploadImageUseCase = mockk()
    private val getUserEmailUseCase: GetUserEmailUseCase = mockk()
    private val logOutUseCase: LogOutUseCase = mockk()

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        viewModel = ProfileViewModel(
            getUserByEmailUseCase,
            updateUserNameUseCase,
            updateUserPasswordUseCase,
            updateUserImgUseCase,
            uploadImageUseCase,
            getUserEmailUseCase,
            logOutUseCase
        )
    }

    @Test
    fun `getUser actualiza el estado a Success cuando se encuentra el usuario`() = runTest {
        val user = UserDto("1", "test@mail.com", "Test User", "imgUrl")
        coEvery { getUserEmailUseCase.invoke() } returns "test@mail.com"
        coEvery { getUserByEmailUseCase.invoke("test@mail.com") } returns Response.success(user)

        viewModel.getUser()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Success)
        Assert.assertEquals(user.fullName, viewModel.name.value)
        Assert.assertEquals(user.email, viewModel.email.value)
        Assert.assertEquals(user.userImageUrl, viewModel.image.value)
        Assert.assertEquals("********", viewModel.password.value)
    }

    @Test
    fun `getUser setea UIState Error si el email está vacío`() = runTest {
        coEvery { getUserEmailUseCase.invoke() } returns ""

        viewModel.getUser()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals(
            "No se pudo recuperar el usuario",
            (viewModel.uiState as UIState.Error).message
        )
    }

    @Test
    fun `getUser setea UIState Error si no se encuentra el usuario`() = runTest {
        coEvery { getUserEmailUseCase.invoke() } returns "test@mail.com"
        coEvery { getUserByEmailUseCase.invoke("test@mail.com") } returns Response.success(null)

        viewModel.getUser()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
    }

    @Test
    fun `getUser setea UIState Error en IOException`() = runTest {
        coEvery { getUserEmailUseCase.invoke() } returns "test@mail.com"
        coEvery { getUserByEmailUseCase.invoke("test@mail.com") } throws IOException()

        viewModel.getUser()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals(
            "Sin conexión a internet",
            (viewModel.uiState as UIState.Error).message
        )
    }

    @Test
    fun `updateName emite snackbar y actualiza el estado a success`() = runTest {
        val user = UserDto("1", "mail@mail.com", "New Name", "imgUrl")
        viewModel.name.value = "New Name"
        viewModel.email.value = "mail@mail.com"
        coEvery {
            updateUserNameUseCase.invoke(
                "mail@mail.com",
                "New Name"
            )
        } returns Response.success(user)

        val messages = mutableListOf<String>()
        val job = launch {
            viewModel.snackbarMessage.collect { messages.add(it) }
        }

        viewModel.updateName()
        advanceUntilIdle()

        Assert.assertTrue(messages.any { it.contains("Nombre actualizado correctamente") })
        Assert.assertTrue(viewModel.uiState is UIState.Success)
        job.cancel()
    }

    @Test
    fun `updateName emite error snackbar en IOException`() = runTest {
        viewModel.name.value = "New Name"
        viewModel.email.value = "mail@mail.com"
        coEvery { updateUserNameUseCase.invoke(any(), any()) } throws IOException()

        val messages = mutableListOf<String>()
        val job = launch {
            viewModel.snackbarMessage.collect { messages.add(it) }
        }

        viewModel.updateName()
        advanceUntilIdle()

        Assert.assertTrue(messages.any { it.contains("Sin conexión a internet") })
        job.cancel()
    }

    @Test
    fun `toggleEditName setea editName true y editPassword false`() {
        viewModel.toggleEditName()
        Assert.assertTrue(viewModel.editName.value)
        Assert.assertFalse(viewModel.editPassword.value)
    }

    @Test
    fun `toggleEditPassword setea editPassword true, editName false y resetea password`() {
        viewModel.toggleEditPassword()
        Assert.assertTrue(viewModel.editPassword.value)
        Assert.assertFalse(viewModel.editName.value)
        Assert.assertEquals("", viewModel.password.value)
    }

    @Test
    fun `cancelEdit deshabilita editar flags y resetea la password`() {
        viewModel.cancelEdit()
        Assert.assertFalse(viewModel.editName.value)
        Assert.assertFalse(viewModel.editPassword.value)
        Assert.assertEquals("********", viewModel.password.value)
    }

    @Test
    fun `onRegisterChange nombre actualiza isFormValid`() {
        viewModel.onRegisterChange("Valid Name")
        Assert.assertEquals("Valid Name", viewModel.name.value)
    }

    @Test
    fun `onRegisterChange password actualiza isFormValid`() {
        viewModel.onRegisterChange("pass", "pass")
        Assert.assertEquals("pass", viewModel.password.value)
        Assert.assertEquals("pass", viewModel.confirmPassword.value)

    }
}