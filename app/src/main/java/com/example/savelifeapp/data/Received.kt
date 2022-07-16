package com.example.savelifeapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Received(
    val namaPasien: String,
    val profileUpload: Int,
    val alamat: String,
    val golDarah: String
) : Parcelable