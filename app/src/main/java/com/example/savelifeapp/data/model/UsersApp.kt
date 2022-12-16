package com.example.savelifeapp.data.model


data class UsersApp(
    var uuid: String? = null,
    val nama: String? = null,
    val email: String? = null,
    val password: String? = null,
    val golDarah: String? = null,
//    ini no wa
    val hone: String? = null,
    val address: String? = null,
    val data: String? = null,
    val image: String? = "",
    val pekerjaan:String = "",
//    val myRequest:List<CreateRequest>
)
