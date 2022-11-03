package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Request(
    val id: String? = null,
    val name: String? = "",
    val golDarah: String? = "",
    val lokasi: String? = "",
    val keterangan: String? = "",
    val whatsapp: String? = ""
) : Parcelable

@Parcelize
data class jenisDarah(
    val jenis: String
) : Parcelable
