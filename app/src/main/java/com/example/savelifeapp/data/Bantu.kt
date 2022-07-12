package com.example.savelifeapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bantu(
    val namePasien: String,
    val lokasiPasien: String,
    val goldarah: String,
    val profilePasien: Int,
) : Parcelable