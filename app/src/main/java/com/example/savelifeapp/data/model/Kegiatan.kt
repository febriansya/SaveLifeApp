package com.example.savelifeapp.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kegiatan(
    val idKegiatan: String? = null,
    val nameKegiatan: String? = null,
    val lokasiKegiatan: String? = null,
    val timeStamp: Timestamp= Timestamp.now(),
    val jamKegiatan: String? = null,
    val img: String? = null,
    val maps:String? = null,
    val infoLanjut:String? = null
) : Parcelable