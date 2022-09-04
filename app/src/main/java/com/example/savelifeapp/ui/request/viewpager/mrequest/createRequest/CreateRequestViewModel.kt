package com.example.savelifeapp.ui.request.viewpager.mrequest.createRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.data.model.createPermintaan.CreateRequest
import com.example.savelifeapp.data.repository.AccountRespository
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateRequestViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val accountRepository: AccountRespository
) : ViewModel() {
    private val _createRequest = MutableLiveData<UiState<String>>()
    val createRequest: LiveData<UiState<String>>
        get() = _createRequest

    fun createRequest(
        createRequest: CreateRequest
    ) {
        _createRequest.value = UiState.Loading
        accountRepository.createRequest(createRequest) {
            _createRequest.value = it
        }
    }

}