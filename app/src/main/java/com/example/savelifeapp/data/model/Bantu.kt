package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bantu(
    val id: String? = "",
    val idPengirim: String? = "",
    val namaPengirim:String? = "",
    val name: String? = "",
    val golDarah: String? = "",
    val lokasi: String? = "",
    val keterangan: String? = "",
    val image: String? = null,
    val whatsapp: String? = null,
    val photoBukti: String? = null

) : Parcelable

//test coding di linux
//test but branch baru