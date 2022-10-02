package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.Kegiatan
import com.example.savelifeapp.data.model.createPermintaan.CreateRequest
import com.example.savelifeapp.ui.home.viepager.kegiatan.KegiatanAdapater
import com.example.savelifeapp.ui.request.viewpager.mrequest.MrequestAdapter
import com.example.savelifeapp.utils.UiState
interface HomeRepository {

    suspend fun getKegiatan(
        arrayList: ArrayList<Kegiatan>,
        adapter: KegiatanAdapater,
        result: (UiState<List<Kegiatan>>) -> Unit
    )
}