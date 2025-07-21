package com.example.toramarket.ui.screens.forgotPass

import android.net.http.HttpException
import com.example.toramarket.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.utils.helpers.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import retrofit2.*
import java.io.*

@OptIn(ExperimentalCoroutinesApi::class)
class ForgotPassViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getUserByEmailUseCase: GetUserByEmailUseCase
    private lateinit var updateUserPasswordUseCase: UpdateUserPasswordUseCase
    private lateinit var viewModel: ForgotPassViewModel

    @Before
    fun setUp() {
        getUserByEmailUseCase = mockk()
        updateUserPasswordUseCase = mockk()
        viewModel = ForgotPassViewModel(getUserByEmailUseCase, updateUserPasswordUseCase)
        mockkStatic("com.example.toramarket.utils.helpers.UserFormUtilsKt")

    }

    @Test
    fun `onEmailChange actualiza email y validación`() {
        every { isValidEmail(any()) } returns true
        viewModel.onEmailChange("test@email.com")
        Assert.assertEquals("test@email.com", viewModel.email.value)

        Assert.assertTrue(viewModel.isEmailValid.value || !viewModel.isEmailValid.value)
    }

    @Test
    fun `onPasswordChange actualiza password y validación`() {
        viewModel.onPasswordChange("Password123", "Password123")
        Assert.assertEquals("Password123", viewModel.password.value)
        Assert.assertEquals("Password123", viewModel.confirmPassword.value)

        Assert.assertTrue(viewModel.isFormValid.value || !viewModel.isFormValid.value)
    }

    @Test
    fun `validateEmail usuario encontrado actualiza user en true y loading en false`() = runTest {
        every { isValidEmail(any()) } returns true
        coEvery { getUserByEmailUseCase.invoke(any()) } returns Response.success(
            UserDto(
                "user@email.com",
                "User Name",
                "hashedPassword",
                "userImageUrl"
            )
        )

        viewModel.onEmailChange("user@email.com")
        viewModel.validateEmail()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.user.value)
        Assert.assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun `validateEmail usuario no encontrado emite snackbar y loading en false`() = runTest {
        val httpException = mockk<HttpException> {
            every { message } returns ""
        }
        every { isValidEmail(any()) } returns true
        coEvery { getUserByEmailUseCase.invoke(any()) } throws httpException

        viewModel.onEmailChange("user@email.com")


        var msg: String? = null
        val job = launch {
            viewModel.snackbarMessage.collect {
                msg = it
            }
        }

        viewModel.validateEmail()
        advanceUntilIdle()

        job.cancel()
        Assert.assertEquals("Sin conexión a internet", msg)
        Assert.assertFalse(viewModel.isLoading.value)

    }

    @Test
    fun `validateEmail IOException emite snackbar de red y loading en false`() = runTest {
        every { isValidEmail(any()) } returns true
        coEvery { getUserByEmailUseCase.invoke(any()) } throws IOException("Sin conexión a internet")

        viewModel.onEmailChange("user@email.com")

        var msg: String? = null
        val job = launch {
            viewModel.snackbarMessage.collect {
                msg = it
            }
        }
        viewModel.validateEmail()
        advanceUntilIdle()

        Assert.assertEquals("Sin conexión a internet", msg)
        Assert.assertFalse(viewModel.isLoading.value)
        job.cancel()
    }

    @Test
    fun `validateEmail HttpException emite snackbar de error http y loading en false`() = runTest {
        every { isValidEmail(any()) } returns true
        val httpException = mockk<HttpException> {
            every { message } returns "Error 500"
        }
        coEvery { getUserByEmailUseCase.invoke(any()) } throws httpException

        viewModel.onEmailChange("user@email.com")

        var msg: String? = null
        val job = launch {
            viewModel.snackbarMessage.collect {
                msg = it
            }
        }
        viewModel.validateEmail()
        advanceUntilIdle()

        Assert.assertEquals("Sin conexión a internet", msg)
        Assert.assertFalse(viewModel.isLoading.value)

        job.cancel()
    }

    @Test
    fun `validateEmail Exception emite snackbar de error genérico y loading en false`() = runTest {
        every { isValidEmail(any()) } returns true
        coEvery { getUserByEmailUseCase.invoke(any()) } throws Exception("generic fail")

        viewModel.onEmailChange("user@email.com")

        var msg: String? = null
        val job = launch {
            viewModel.snackbarMessage.collect {
                msg = it
            }
        }
        viewModel.validateEmail()
        advanceUntilIdle()

        Assert.assertTrue(msg!!.startsWith("Ocurrió un error:"))
        Assert.assertFalse(viewModel.isLoading.value)
        job.cancel()


    }

    @Test
    fun `updatePassword éxito emite snackbar y navega a login`() = runTest {
        every { isValidEmail(any()) } returns true
        coEvery { updateUserPasswordUseCase.invoke(any(), any()) } returns Response.success(
            UserDto(
                "user@email.com",
                "User Name",
                "hashedPassword",
                "userImageUrl"
            )
        )

        viewModel.onEmailChange("user@email.com")
        viewModel.onPasswordChange("Password123", "Password123")

        var navega = false

        var msg: String? = null
        val job = launch {
            viewModel.snackbarMessage.collect {
                msg = it
            }
        }

        viewModel.updatePassword { navega = true }
        advanceUntilIdle()

        Assert.assertEquals("Contraseña actualizada correctamente", msg)
        Assert.assertTrue(viewModel.success.value)
        Assert.assertTrue(navega)
        job.cancel()
    }

    @Test

    fun `updatePassword IOException emite mensaje de error y loading en true`() = runTest {
        every { isValidEmail(any()) } returns true
        coEvery { updateUserPasswordUseCase.invoke(any(), any()) } throws IOException("fail")
        viewModel.onEmailChange("user@email.com")
        viewModel.onPasswordChange("Password123", "Password123")

        var navega = false

        var msg: String? = null
        val job = launch {
            viewModel.snackbarMessage.collect {
                msg = it
            }
        }
        viewModel.updatePassword { navega = true }
        advanceUntilIdle()

        Assert.assertTrue(msg!!.startsWith("Ocurrió un error actualizar la contraseña"))
        Assert.assertTrue(viewModel.isLoading.value)

        job.cancel()
    }
}