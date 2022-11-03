package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.ui.request.viewpager.myRequest.MrequestAdapter
import com.example.savelifeapp.ui.request.viewpager.receivedRequest.ReceivedAdapter
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

    //    delete my request
    suspend fun delMyRequest(
        arrayList: CreateRequest,
        result: (UiState<String>) -> Unit,
    )

    //    terima permintaan
    suspend fun acceptRequest(

    )

    //    tampilkan permintaan tolong
    suspend fun showHelpRequest(
        arrayList: ArrayList<Received>,
        adapter: ReceivedAdapter,
        result: (UiState<List<Received>>) -> Unit
    )
}