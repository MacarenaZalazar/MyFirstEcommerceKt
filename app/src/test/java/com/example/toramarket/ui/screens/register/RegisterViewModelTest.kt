package com.example.toramarket.ui.screens.register

import com.example.toramarket.*
import com.example.toramarket.data.remote.dto.*
import com.example.toramarket.domain.repository.*
import com.example.toramarket.utils.helpers.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: UserRepository
    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        repo = mockk()
        viewModel = RegisterViewModel(repo)
        mockkStatic("com.example.toramarket.utils.helpers.UserFormUtilsKt")
        mockkStatic("com.example.toramarket.utils.helpers.HashUtilsKt")
    }

    @Test
    fun `onRegisterChange actualiza los campos y valida el formulario correctamente (válido)`() {
        every { isValidEmail("test@email.com") } returns true
        every { isValidPassword("Password123") } returns true

        viewModel.onRegisterChange("John Doe", "test@email.com", "Password123", "Password123")
        assertEquals("John Doe", viewModel.name.value)
        assertEquals("test@email.com", viewModel.email.value)
        assertEquals("Password123", viewModel.password.value)
        assertEquals("Password123", viewModel.confirmPassword.value)
        assertTrue(viewModel.isFormValid.value)
    }

    @Test
    fun `onRegisterChange valida el formulario como inválido si los datos no son correctos`() {
        every { isValidEmail("bademail") } returns false
        every { isValidPassword("123") } returns false

        viewModel.onRegisterChange("", "bademail", "123", "456")
        assertFalse(viewModel.isFormValid.value)
    }

    @Test
    fun `register establece success en true y llama a toHome en registro exitoso`() = runTest {
        every { hashPasswordSHA256(any()) } returns "hashed"
        val user = UserDto(
            "John Doe", "test@email.com", "Password123",
            "imagen",
        )

        every { isValidEmail(any()) } returns true
        every { isValidPassword(any()) } returns true
        every { hashPasswordSHA256(any()) } returns "hashed"
        coEvery { repo.register(any()) } returns user

        viewModel.onRegisterChange("John Doe", "test@email.com", "Password123", "Password123")

        var navega = false
        viewModel.register { navega = true }
        advanceUntilIdle()

        assertTrue(viewModel.success.value)
        assertTrue(navega)
        assertTrue(viewModel.isLoading.value)
    }

    @Test
    fun `register establece success en false y isLoading en false si hay error`() = runTest {
        every { isValidEmail(any()) } returns true
        every { isValidPassword(any()) } returns true
        every { hashPasswordSHA256(any()) } returns "hashed"
        coEvery { repo.register(any()) } throws Exception("fail")

        viewModel.onRegisterChange("John Doe", "test@email.com", "Password123", "Password123")

        var navega = false
        viewModel.register { navega = true }
        advanceUntilIdle()

        assertFalse(viewModel.success.value)
        assertFalse(viewModel.isLoading.value)
        assertFalse(navega)
    }
}