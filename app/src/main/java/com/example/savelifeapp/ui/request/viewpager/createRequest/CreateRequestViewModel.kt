package com.example.savelifeapp.ui.request.viewpager.createRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.data.repository.AccountRespository
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.ui.request.viewpager.myRequest.MrequestAdapter
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

    private val _delRequest = MutableLiveData<UiState<String>>()
    val delRequest: LiveData<UiState<String>>
        get() = _delRequest

    private val _updateRequest = MutableLiveData<UiState<String>>()
    val updateRequest: LiveData<UiState<String>>
        get() = _updateRequest


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

    fun deleteRequest(
        arrayList: CreateRequest,
        id: String
    ) {
        viewModelScope.launch {
            accountRepository.deleteRequest(
                request = arrayList,
                id,
            ) {
                _delRequest.value = it
            }
        }
    }

    fun updateRequest(
        arrayList: CreateRequest,
        id: String
    ) {
        viewModelScope.launch {
            accountRepository.updateRequest(
                request = arrayList,
                id
            ) {
                _updateRequest.value = it
            }
        }
    }
}