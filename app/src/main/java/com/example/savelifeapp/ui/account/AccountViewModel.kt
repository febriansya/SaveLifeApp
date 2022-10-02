package com.example.savelifeapp.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.data.repository.AccountRespository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val repository: AccountRespository,
    val authRepository: AuthRepository
) : ViewModel() {
    //    logout
    private val _logout = MutableLiveData<UiState<String>>()
    val logout: LiveData<UiState<String>>
        get() = _logout

    fun logout() {
        _logout.value = UiState.Loading
        authRepository.logout() {
            _logout.value = it
        }
    }
}