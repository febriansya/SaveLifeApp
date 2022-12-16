package com.example.savelifeapp.ui.request.viewpager.receivedRequest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savelifeapp.data.model.*
import com.example.savelifeapp.data.repository.AccountRespository
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.data.repository.RequestRepository
import com.example.savelifeapp.ui.account.HistoryAdapter
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReceivedViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val accountRepository: AccountRespository,
    val requestRepository: RequestRepository
) : ViewModel() {
    private val _receiveRequest = MutableLiveData<UiState<String>>()
    val receiveRequest: LiveData<UiState<String>>
        get() = _receiveRequest

    private val _getRequest = MutableLiveData<UiState<List<Received>>>()
    val getRequest: LiveData<UiState<String>>
        get() = _receiveRequest


    private val _acceptRequest = MutableLiveData<UiState<String>>()
    val acceptRequest: LiveData<UiState<String>>
        get() = _acceptRequest

    private val _tolakRequest = MutableLiveData<UiState<String>>()
    val tolakRequest: LiveData<UiState<String>>
        get() = _tolakRequest


    private val _showHistory = MutableLiveData<UiState<String>>()
    val showHistory: LiveData<UiState<String>>
        get() = _showHistory


    private val _buatRiwayat = MutableLiveData<UiState<String>>()
    val buatRiwayat: LiveData<UiState<String>>
        get() = _buatRiwayat


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


    fun compareReceivePendonor(
        arrayListReceived: ArrayList<Received>,
        arrayListCalon: ArrayList<CalonPendonor>
    ) {
        viewModelScope.launch {
            requestRepository.CalonPendonorStatus(arrayListReceived, arrayListCalon)
        }
    }

    fun acceptRequestData(
        calonPendonor: CalonPendonor,
        idUserPeminta: String,
        idRequestPeminta: String,
    ) {
        viewModelScope.launch {
            accountRepository.acceptRequest(
                calonPendonor, idUserPeminta, idRequestPeminta
            ) {
                _acceptRequest.value = it
            }
        }
    }

    fun tolakRequest(
        calonPendonor: CalonPendonor,
        idUserPeminta: String,
        idRequestPeminta: String,
    ) {
        viewModelScope.launch {
            accountRepository.tolakRequest(
                calonPendonor, idUserPeminta, idRequestPeminta
            ) {
                _tolakRequest.value = it
            }
        }
    }

//    fun sudahDonor(
//        historyDonors: HistoryDonors,
//        idRequest: String,
//        namaPasien: String
//    ) {
//        viewModelScope.launch {
//            accountRepository.CreateHistory(historyDonors, idRequest, namaPasien)
//        }
//    }

//    fun buatRiwayat(
//        historyDonors: HistoryDonors,
//        idRequest: String,
//        namaPasien: String,
//    ) {
//        viewModelScope.launch {
//            accountRepository.RiwayatPendonor(historyDonors, idRequest, namaPasien) {
//                _buatRiwayat.value = it
//            }
//        }
//    }


    fun showMyHistory(
        historyDonors: ArrayList<HistoryDonors>,
        adapter: HistoryAdapter
    ) {
        viewModelScope.launch {
            accountRepository.getHistoryDonors(historyDonors, adapter) {
                _showHistory.value = it
            }
        }
    }
}