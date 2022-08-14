package com.example.savelifeapp.ui.account


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savelifeapp.data.model.Profile
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.data.repository.AccountRespository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.processor.internal.definecomponent.codegen._dagger_hilt_android_internal_builders_ServiceComponentBuilder
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.log


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
        authRepository.logout("current") {
            _logout.value = it
        }
    }
}