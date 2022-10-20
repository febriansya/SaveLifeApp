package com.example.savelifeapp.data.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Profile(
    val name: String,
    val image: String,
    val totalDonor: Int? = null,
    val golDarah: String,
) : Parcelable
