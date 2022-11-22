package com.example.savelifeapp.ui.request

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savelifeapp.data.db.CalonEntity
import com.example.savelifeapp.data.db.PendonorDao
import com.example.savelifeapp.data.repository.AccountRespository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(val repository: AccountRespository) : ViewModel() {
}