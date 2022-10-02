package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.data.model.createPermintaan.CreateRequest
import com.example.savelifeapp.ui.request.viewpager.mrequest.MrequestAdapter
import com.example.savelifeapp.ui.request.viewpager.received.ReceivedAdapter
import com.example.savelifeapp.utils.UiState

interface AccountRespository {
    suspend fun createRequest(
        createRequest: CreateRequest,
        result: (UiState<String>) -> Unit
    )

    //    tampilkan request yang dibuat
    suspend fun showMyRequest(
        arrayList: ArrayList<CreateRequest>,
        adapter: MrequestAdapter,
        result: (UiState<List<CreateRequest>>) -> Unit
    )

    fun acceptRequest()

    //    tampilkan permintaan tolong
    suspend fun showHelpRequest(
        arrayList: ArrayList<Received>,
        adapter: ReceivedAdapter,
        result: (UiState<List<Received>>) -> Unit
    )
}