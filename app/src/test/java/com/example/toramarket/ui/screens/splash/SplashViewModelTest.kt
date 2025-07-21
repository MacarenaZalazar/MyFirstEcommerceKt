package com.example.toramarket.ui.screens.splash

import com.example.toramarket.*
import com.example.toramarket.domain.user.*
import com.example.toramarket.ui.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var isUserLoggedInUseCase: IsUserLoggedInUseCase
    private lateinit var viewModel: SplashViewModel

    @Before
    fun setUp() {
        isUserLoggedInUseCase = mockk()
        viewModel = SplashViewModel(isUserLoggedInUseCase)
    }


    @Test
    fun `uiState es Success true cuando el usuario está logueado`() = runTest {
        coEvery { isUserLoggedInUseCase.invoke() } returns true

        viewModel.getLoggedUser()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Success)
        Assert.assertEquals(true, (viewModel.uiState as UIState.Success<Boolean>).data)
    }

    @Test
    fun `uiState es Success false cuando el usuario no está logueado`() = runTest {
        coEvery { isUserLoggedInUseCase.invoke() } returns false

        viewModel.getLoggedUser()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Success)
        Assert.assertEquals(false, (viewModel.uiState as UIState.Success<Boolean>).data)
    }

    @Test
    fun `uiState es Error cuando ocurre una excepción`() = runTest {
        coEvery { isUserLoggedInUseCase.invoke() } throws Exception("fail")

        viewModel.getLoggedUser()
        advanceUntilIdle()

        Assert.assertTrue(viewModel.uiState is UIState.Error)
        Assert.assertEquals(
            "Ocurrió un error inesperado",
            (viewModel.uiState as UIState.Error).message
        )
    }
}