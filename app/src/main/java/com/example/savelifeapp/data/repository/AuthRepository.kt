package com.example.savelifeapp.data.repository


import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.utils.UiState

interface AuthRepository {
    fun login(email: String, password: String, result: (UiState<String>) -> Unit)
    fun updateUserInfo(
        user: UsersApp,
        result: (UiState<String>) -> Unit
    )

    fun registerUser(
        email: String,
        password: String,
        user: UsersApp, result: (UiState<String>) -> Unit
    )

    fun logout(result: (UiState<String>) -> Unit)
    fun forgotPassword(email: String, result: (UiState<String>) -> Unit)
    fun updatePassword(password:String, result: (UiState<String>) -> Unit)
}