package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.Bantu
import com.example.savelifeapp.data.model.Kegiatan
import com.example.savelifeapp.ui.home.viepager.bantu.BantuAdapater
import com.example.savelifeapp.ui.home.viepager.kegiatan.KegiatanAdapater
import com.example.savelifeapp.utils.UiState
interface HomeRepository {
    suspend fun getKegiatan(
        arrayList: ArrayList<Kegiatan>,
        adapter: KegiatanAdapater,
        result: (UiState<List<Kegiatan>>) -> Unit
    )
    suspend fun getAllPasien(
        arrayList: ArrayList<Bantu>,
        adapter:BantuAdapater,
        result:(UiState<List<Bantu>>) -> Unit
    )
}