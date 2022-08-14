package com.example.savelifeapp.data.repository


import android.content.Context
import android.util.Log
import com.example.savelifeapp.data.model.Profile
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.utils.FireStoreCollection
import com.example.savelifeapp.utils.FireStoreDocumentField
import com.example.savelifeapp.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class AccountRepositoryImpl(
    val database: FirebaseFirestore,
    val auth: FirebaseAuth,
) : AccountRespository {

}