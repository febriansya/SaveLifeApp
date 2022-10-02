package com.example.savelifeapp.ui.home.viepager.kegiatan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savelifeapp.data.model.Kegiatan
import com.example.savelifeapp.data.model.createPermintaan.CreateRequest
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.data.repository.HomeRepository
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class KegiatanViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val homeRepository: HomeRepository
) : ViewModel() {

    private val _kegiatanUi = MutableLiveData<UiState<String>>()

    private val _kegiatan = MutableLiveData<UiState<List<Kegiatan>>>()
    val kegiatan: LiveData<UiState<String>>
        get() = _kegiatanUi

    fun getDataKegiatan(
        arrayList: ArrayList<Kegiatan>,
        adapter: KegiatanAdapater
    ) {
        viewModelScope.launch {
            _kegiatan.value = UiState.Loading
            homeRepository.getKegiatan(
                arrayList,
                adapter
            ) {
                _kegiatan.value = it
            }
        }
    }
}