package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Request(
    val name: String? = "",
    val golDarah: String? = "",
    val lokasi: String? = "",
    val keterangan: String? = ""
)

@Parcelize
data class jenisDarah(
    val jenis: String
) : Parcelable
