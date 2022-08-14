package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.utils.UiState

interface AuthRepository {
    fun login(email: String, password: String, result: (UiState<String>) -> Unit)
    fun logout(user: String, result: (UiState<String>) -> Unit)
}