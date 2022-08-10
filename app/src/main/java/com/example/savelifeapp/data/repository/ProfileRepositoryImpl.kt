package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.Profile
import com.example.savelifeapp.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileRepositoryImpl(val database: FirebaseFirestore) :
    ProfileRespository {

    lateinit var auth:FirebaseAuth

    override fun getProfile(): List<Profile> {
        return arrayListOf(
            Profile(
                name = "yan",
                golDarah = "AB+"
            )
        )
    }
}