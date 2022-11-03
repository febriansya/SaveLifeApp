package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalonPendonor(
    var id:String?= null,
    var name:String?=null,
    var image:String? = null,
    var whatsapp:String?=null,
    var alamat:String?=null
):Parcelable