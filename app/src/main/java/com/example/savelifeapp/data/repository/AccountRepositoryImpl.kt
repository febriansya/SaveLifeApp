package com.example.savelifeapp.data.repository


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountRepositoryImpl(
    val database: FirebaseFirestore,
    val auth: FirebaseAuth,
) : AccountRespository {

}