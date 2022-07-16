package com.example.savelifeapp.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stok(
    val angkaStok: String,
    val golDarah: String,
) : Parcelable
