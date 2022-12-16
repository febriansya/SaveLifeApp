package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalonPendonor(
    var idPendonor: String? = "",
    var nama: String? = "",
    var image: String? = "",
    var hone: String? = "",
    var address: String? = "",
    var idAccRequest: String? = "",
    var status: String? = ""
) : Parcelable