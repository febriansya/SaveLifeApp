package com.example.savelifeapp.data.model.createPermintaan


data class CreateRequest(
    val name: String? = "",
    val golDarah: String? = "",
    val lokasi: String? = "",
    val keterangan: String? = "",
    val image: String? = null,
    val whatsapp: String? = ""
)