package com.example.savelifeapp.ui.request.viewpager.createRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savelifeapp.data.model.createPermintaan.CreateRequest
import com.example.savelifeapp.data.repository.AccountRespository
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.ui.request.viewpager.mrequest.MrequestAdapter
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRequestViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val accountRepository: AccountRespository,
) : ViewModel() {
    private val _createRequest = MutableLiveData<UiState<String>>()
    val createRequest: LiveData<UiState<String>>
        get() = _createRequest

    private val _getRequest = MutableLiveData<UiState<List<CreateRequest>>>()
    val getRequest: LiveData<UiState<String>>
        get() = _createRequest


    fun createRequest(
        createRequest: CreateRequest
    ) {
        viewModelScope.launch {
            _createRequest.value = UiState.Loading
            accountRepository.createRequest(createRequest) {
                _createRequest.value = it
            }
        }
    }

    //    this for data request user.
    fun getDataRequest(
        arrayList: ArrayList<CreateRequest>,
        adapter: MrequestAdapter,
    ) {
        viewModelScope.launch {
            _createRequest.value = UiState.Loading
            accountRepository.showMyRequest(
                arrayList = arrayList,
                adapter = adapter,
            ) {
                _getRequest.value = it
            }
        }
    }
}