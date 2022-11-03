package com.example.savelifeapp.ui.request.viewpager.receivedRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.data.repository.AccountRespository
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReceivedViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val accountRepository: AccountRespository,
) : ViewModel() {
    private val _receiveRequest = MutableLiveData<UiState<String>>()
    val receiveRequest: LiveData<UiState<String>>
        get() = _receiveRequest

    private val _getRequest = MutableLiveData<UiState<List<Received>>>()
    val getRequest: LiveData<UiState<String>>
        get() = _receiveRequest


    fun getReceivedData(
        arrayList: ArrayList<Received>,
        adapter: ReceivedAdapter
    ) {
        _receiveRequest.value = UiState.Loading
        viewModelScope.launch {
            accountRepository.showHelpRequest(arrayList, adapter) {
                _getRequest.value = it
            }
        }
    }
}