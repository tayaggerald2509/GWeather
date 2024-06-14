package com.exam.gweather.ui.authentication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.exam.gweather.data.repository.auth.AuthRepository
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.notNull
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var authViewModel: AuthViewModel

    @Mock
    private lateinit var authRepository: AuthRepository

    @Mock
    private lateinit var authResult: AuthResult

    @Mock
    private lateinit var authObserver: Observer<Result<AuthResult>>

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        authViewModel = AuthViewModel(authRepository)
        authViewModel.authResult.observeForever(authObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loginUser success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        whenever(authRepository.loginUser(email, password)).thenReturn(Result.success(authResult))

        // When
        authViewModel.signIn(email, password)

        // Then
        verify(authObserver).onChanged(Result.success(authResult))

        assertThat(authViewModel.authResult.value).isEqualTo(Result.success(authResult))
    }

    @Test
    fun `registerUser success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        whenever(authRepository.signUp(email, password)).thenReturn(Result.success(authResult))

        // When
        authViewModel.signUp(email, password)

        // Then
        verify(authObserver).onChanged(Result.success(authResult))
        assertThat(authViewModel.authResult.value).isEqualTo(Result.success(authResult))
    }

}