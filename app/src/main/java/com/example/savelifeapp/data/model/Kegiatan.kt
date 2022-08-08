package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kegiatan(
    val nameKegiatan: String,
    val lokasiKegiatan: String,
    val tanggalKegiatan: String,
    val jamKegiatan: String,
    val profilePmi: Int,
) : Parcelable