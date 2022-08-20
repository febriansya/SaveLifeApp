package com.example.savelifeapp.ui.signUp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    val authRepository: AuthRepository
) : ViewModel() {
    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    fun register(
        email: String,
        password: String,
        usersApp: UsersApp
    ) {
        _register.value = UiState.Loading
        authRepository.registerUser(
            email,
            password,
            user = usersApp,
        ) {
            _register.value = it
        }
    }
}