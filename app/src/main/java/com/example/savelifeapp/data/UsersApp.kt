package com.example.savelifeapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UsersApp(
   val uuid:String,
   val nama: String,
   val email: String,
   val password: String,
   val golDarah: String,
   val hone: String,
   val address: String,
   val data: String,
):Parcelable
