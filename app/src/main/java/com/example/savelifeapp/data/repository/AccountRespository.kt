package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.data.model.createPermintaan.CreateRequest
import com.example.savelifeapp.utils.UiState


interface AccountRespository {
    fun createRequest(
            createRequest: CreateRequest,
            result:(UiState<String>)->Unit
    )
    fun acceptRequest()

}