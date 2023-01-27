package com.example.savelifeapp.ui.login.forgotPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    val authRepository: AuthRepository
) : ViewModel() {
    private val _forgotPassword = MutableLiveData<UiState<String>>()
    val forgotPassword: LiveData<UiState<String>>
        get() = _forgotPassword


    private val _updatePassword = MutableLiveData<UiState<String>>()
    val updatePassword: LiveData<UiState<String>>
        get() = _updatePassword


    fun ResetPassword(email: String) {
        _forgotPassword.value = UiState.Loading
        authRepository.forgotPassword(email) {
            _forgotPassword.value = it
        }
    }


    fun UpdatePassword(password:String){
        _updatePassword.value = UiState.Loading
        authRepository.updatePassword(password){
            _updatePassword.value = it
        }
    }

}