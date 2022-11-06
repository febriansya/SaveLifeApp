package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.CalonPendonor
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.ui.request.viewpager.detailRequest.CalonPendonorRequestAdapter
import com.example.savelifeapp.ui.request.viewpager.myRequest.MrequestAdapter
import com.example.savelifeapp.ui.request.viewpager.receivedRequest.ReceivedAdapter
import com.example.savelifeapp.utils.UiState

interface AccountRespository {

    suspend fun createRequest(
        createRequest: CreateRequest, result: (UiState<String>) -> Unit
    )

    //    tampilkan request yang dibuat
    suspend fun showMyRequest(
        arrayList: ArrayList<CreateRequest>,
        adapter: MrequestAdapter,
        result: (UiState<List<CreateRequest>>) -> Unit
    )

    suspend fun deleteRequest(request: CreateRequest, id: String, result: (UiState<String>) -> Unit)

    suspend fun updateRequest(request: CreateRequest, id: String, result: (UiState<String>) -> Unit)


    //    terima permintaan
    suspend fun acceptRequest(
        calonPendonor: CalonPendonor,
        idUserPeminta: String,
        idRequestPeminta: String,
        result: (UiState<String>) -> Unit
    )

    suspend fun calonPendonor(
        arrayList: ArrayList<CalonPendonor>,
        idRequest:String,
        adapter: CalonPendonorRequestAdapter,
        result: (UiState<List<CalonPendonor>>) -> Unit
    )

    //    tampilkan permintaan tolong
    suspend fun showHelpRequest(
        arrayList: ArrayList<Received>,
        adapter: ReceivedAdapter,
        result: (UiState<List<Received>>) -> Unit
    )
}