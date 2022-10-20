package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kegiatan(
    val idKegiatan: String? = null,
    val nameKegiatan: String? = null,
    val lokasiKegiatan: String? = null,
    val tanggalKegiatan: String? = null,
    val jamKegiatan: String? = null,
    val img: String? = null,
) : Parcelable