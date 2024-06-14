package com.exam.gweather.data.repository.auth

import com.google.firebase.auth.AuthResult

interface AuthRepository {

    suspend fun loginUser(email: String, password: String) : Result<AuthResult>
    suspend fun signUp(email: String, password: String): Result<AuthResult>
    fun signOut()
    fun getCurrentUser(): String?
}