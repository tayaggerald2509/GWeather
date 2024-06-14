package com.exam.gweather.ui.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.gweather.data.repository.auth.AuthRepository
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean> get() = _isUserLoggedIn

    private val _authResult = MutableLiveData<Result<AuthResult>>()
    val authResult: LiveData<Result<AuthResult>> get() = _authResult

//    private val _registrationResult = MutableLiveData<Result<AuthResult>>()
//    val registrationResult: LiveData<Result<AuthResult>> get() = _registrationResult

//    init {
//        checkUserLoggedIn()
//    }

     fun checkUserLoggedIn() {
        viewModelScope.launch(Dispatchers.IO) {
            _isUserLoggedIn.value = authRepository.getCurrentUser() != null
        }
    }

    fun signIn(email: String, password: String) {

        viewModelScope.launch {
            _authResult.value = authRepository.loginUser(email, password)
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authResult.value = authRepository.signUp(email, password)
        }
    }

}