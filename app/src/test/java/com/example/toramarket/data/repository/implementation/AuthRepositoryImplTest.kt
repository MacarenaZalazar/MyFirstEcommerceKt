package com.example.toramarket.data.repository.implementation

import com.example.toramarket.*
import com.example.toramarket.data.remote.api.*
import com.example.toramarket.data.remote.dto.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import retrofit2.*


@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val api = mockk<AuthService>()
    private val repo = AuthRepositoryImpl(api)

    @Test
    fun `login retorna la respuesta del servicio correctamente`() = runTest {
        val request = AuthRequest("usuario", "contraseña")
        val expectedResponse = Response.success(
            LoginResponse(
                message = "login exitoso",
                user = LoggerUserDto(
                    id = "123",
                    fullName = "usuario",
                    email = "",
                    encryptedPassword = "password",
                    userImageUrl = "img_url",
                ),
            )
        )
        coEvery { api.login(request) } returns expectedResponse

        val result = repo.login(request)

        assertEquals(expectedResponse, result)
    }
}