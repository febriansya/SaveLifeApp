package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateRequest(
    var id: String? = "",
    var idPengirim: String? = "",
    var namaPengirim:String?="",
    var name: String? = "",
    val golDarah: String? = "",
    val lokasi: String? = "",
    val keterangan: String? = "",
    val image: String? = null,
    val whatsapp: String? = "",
    val photoBukti: String? = null
) : Parcelable