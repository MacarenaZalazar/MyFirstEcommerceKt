package com.example.toramarket.data.repository.implementation

import com.example.toramarket.*
import com.example.toramarket.data.local.*
import com.example.toramarket.data.remote.api.*
import com.example.toramarket.data.remote.dto.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import retrofit2.*

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private lateinit var api: UserService
    private lateinit var local: UserDataStore
    private lateinit var repositorio: UserRepositoryImpl


    @Before
    fun setUp() {
        api = mockk()
        local = mockk()
        repositorio = UserRepositoryImpl(api, local)
    }

    @Test
    fun `registrar usuario llama al API y retorna el usuario`() = runTest() {
        val registro = UserRegisterDto(
            fullName = "Nombre",
            email = "email@email.com",
            encryptedPassword = "pass"
        )
        val usuario = UserDto("email@email.com", "Nombre", "pass", "img")
        coEvery { api.register(registro) } returns usuario

        val resultado = repositorio.register(registro)

        assertEquals(usuario, resultado)
    }

    @Test
    fun `obtenerEmailUsuario retorna el email guardado localmente`() = runTest() {
        coEvery { local.userEmail } returns flowOf("correo@ejemplo.com")
        val resultado = repositorio.getUserEmail()
        assertEquals("correo@ejemplo.com", resultado)
    }

    @Test
    fun `obtenerIdUsuario retorna el id guardado localmente`() = runTest() {
        coEvery { local.userId } returns flowOf("id123")
        val resultado = repositorio.getUserId()
        assertEquals("id123", resultado)
    }

    @Test
    fun `obtenerUsuarioPorEmail llama al API y retorna el usuario`() = runTest() {
        val usuario = UserDto("email@email.com", "Nombre", "pass", "img")
        coEvery { api.getUserByEmail("email@email.com") } returns Response.success(usuario)
        val resultado = repositorio.getUserByEmail("email@email.com")
        assertTrue(resultado.isSuccessful)
        assertEquals(usuario, resultado.body())
    }

    @Test
    fun `actualizarNombre llama al API y retorna el usuario actualizado`() = runTest() {
        val request = UpdateNameDto("Nuevo Nombre")
        val usuario = UserDto("email@email.com", "Nuevo Nombre", "pass", "img")
        coEvery { api.updateName("email@email.com", request) } returns Response.success(usuario)
        val resultado = repositorio.updateName("email@email.com", request)
        assertTrue(resultado.isSuccessful)
        assertEquals(usuario, resultado.body())
    }

    @Test
    fun `actualizarContraseña llama al API y retorna el usuario actualizado`() =
        runTest() {
            val request = UpdatePassDto("nuevaPassword123")
            val usuario = UserDto("email@email.com", "Nombre", "nuevaPassword123", "img")
            coEvery { api.updatePassword("email@email.com", request) } returns Response.success(
                usuario
            )
            val resultado = repositorio.updatePassword("email@email.com", request)
            assertTrue(resultado.isSuccessful)
            assertEquals(usuario, resultado.body())
        }

    @Test
    fun `actualizarImagenPerfil llama al API y retorna el usuario actualizado`() =
        runTest() {
            val request = UserImgDto("urlImagenNueva")
            val usuario = UserDto("email@email.com", "Nombre", "pass", "urlImagenNueva")
            coEvery { api.updateProfileImg("email@email.com", request) } returns Response.success(
                usuario
            )
            val resultado = repositorio.updateProfileImg("email@email.com", request)
            assertTrue(resultado.isSuccessful)
            assertEquals(usuario, resultado.body())
        }

    @Test
    fun `cerrarSesion llama al método local y no lanza excepción`() = runTest() {
        coEvery { local.clearUser() } returns Unit
        repositorio.logOut()
    }

    @Test
    fun `estaUsuarioLogueado retorna el valor de local`() = runTest() {
        coEvery { local.isUserLoggedIn() } returns true
        val resultado = repositorio.isUserLoggedIn()
        assertTrue(resultado)
    }
}