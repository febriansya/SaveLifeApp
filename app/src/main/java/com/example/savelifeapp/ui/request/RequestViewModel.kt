package com.example.savelifeapp.ui.request

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.data.repository.AccountRespository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(val repository: AccountRespository) : ViewModel() {


}