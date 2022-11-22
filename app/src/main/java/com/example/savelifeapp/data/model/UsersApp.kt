package com.example.savelifeapp.data.model


data class UsersApp(
    var uuid: String,
    val nama: String,
    val email: String,
    val password: String,
    val golDarah: String,
//    ini no wa
    val hone: String,
    val address: String,
    val data: String,
    val image: String? = "",
//    val myRequest:List<CreateRequest>
)
