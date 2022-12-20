package com.example.savelifeapp.ui.home.viepager.bantu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savelifeapp.data.model.Bantu
import com.example.savelifeapp.data.model.Kegiatan
import com.example.savelifeapp.data.repository.AuthRepository
import com.example.savelifeapp.data.repository.HomeRepository
import com.example.savelifeapp.ui.home.viepager.kegiatan.KegiatanAdapater
import com.example.savelifeapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BantuViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val homeRepository: HomeRepository
):ViewModel() {
    private val _bantuUi = MutableLiveData<UiState<String>>()

    private val _bantu = MutableLiveData<UiState<List<Bantu>>>()
    val bantu: LiveData<UiState<String>>
        get() = _bantuUi

    fun getBantuPasien(
        arrayList: ArrayList<Bantu>,
        adapter: BantuAdapater
    ) {
        viewModelScope.launch {
            _bantu.value = UiState.Loading
            homeRepository.getAllPasien(
                arrayList,
                adapter
            ) {
                _bantu.value = it
            }
        }
    }
}