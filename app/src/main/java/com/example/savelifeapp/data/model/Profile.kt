package com.example.savelifeapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Profile(
    val name:String,
    val golDarah:String,
):Parcelable
