package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bantu(
    val namePasien: String,
    val lokasiPasien: String,
    val goldarah: String,
    val profilePasien: Int,
) : Parcelable

//test coding di linux
//test but branch baru