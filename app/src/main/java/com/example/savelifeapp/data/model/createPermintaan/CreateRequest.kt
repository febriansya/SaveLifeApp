package com.example.savelifeapp.data.model.createPermintaan



data class CreateRequest(
    val name: String,
    val golDarah: String,
    val lokasi: String? = null,
    val keterangan: String? = null
)