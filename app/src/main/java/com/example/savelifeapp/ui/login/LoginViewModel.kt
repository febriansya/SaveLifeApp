package com.example.savelifeapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    val auth: AuthRepository
) : ViewModel() {

    //    login
    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login



    fun login(
        email: String,
        password: String
    ) {
        _login.value = UiState.Loading
        auth.login(
            email, password
        ) {
            _login.value = it
        }
    }


}