package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.*
import com.example.savelifeapp.ui.account.HistoryAdapter
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

    //    tolak permintaan
    suspend fun tolakRequest(
        calonPendonor: CalonPendonor,
        idUserPeminta: String,
        idRequestPeminta: String,
        result: (UiState<String>) -> Unit
    )


    suspend fun calonPendonor(
        arrayList: ArrayList<CalonPendonor>,
        idRequest: String,
        adapter: CalonPendonorRequestAdapter,
        result: (UiState<List<CalonPendonor>>) -> Unit
    )

    //    tampilkan permintaan tolong
    suspend fun showHelpRequest(
        arrayList: ArrayList<Received>,
        adapter: ReceivedAdapter,
        result: (UiState<List<Received>>) -> Unit
    )

    //    untuk skip detail received, dimana dalam kondisi sebagai calon pendonor
    suspend fun StatusCalonPendonor(
        arrayList: ArrayList<CalonPendonor>,
        idRequest: String
    )

    //    update account setings
    suspend fun UpdateAccount(
        arrayList: UsersApp,
        result: (UiState<String>) -> Unit
    )

    //    create history Calon Pendonor
//    suspend fun CreateHistory(
//        historyDonors: HistoryDonors,
//        idRequest: String,
//        namaPasien: String,
//    )

    //    another option create history
//    suspend fun RiwayatPendonor(
//        historyDonors: HistoryDonors,
//        idRequest: String,
//        namaPasien: String,
//        result: (UiState<String>) -> Unit
//    )


    //    getHistoryDonors
    suspend fun getHistoryDonors(
        historyDonors: ArrayList<HistoryDonors>,
        adapter: HistoryAdapter,
        result: (UiState<String>) -> Unit
    )
}