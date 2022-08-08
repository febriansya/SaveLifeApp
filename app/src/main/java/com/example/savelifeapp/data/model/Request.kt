package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Request(
    val name: String,
    val golDarah: String,
    val lokasi: String? = null,
    val keterangan: String? = null
) : Parcelable
