package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.CalonPendonor
import com.example.savelifeapp.data.model.Received

interface RequestRepository {

    suspend fun CalonPendonorStatus(
         arrayReceived:ArrayList<Received>,
         arrayCalon:ArrayList<CalonPendonor>,
    )
}