package com.example.savelifeapp.data.repository

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class AuthRepositoryImplement(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore,
    val gson: Gson? = null,
    val context: Context? = null
) : AuthRepository {

    override fun login(email: String, password: String, result: (UiState<String>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    result.invoke(UiState.Success("Login successfully"))
//                    val intent = Intent(context, HomeActivity::class.java)
//                    context?.startActivity(intent)
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure("Authentication failed, pleas check email and password"))
            }
    }

    override fun logout(user: String, result: (UiState<String>) -> Unit) {
        auth.signOut()
        result.invoke(UiState.Success("Logout successfully"))
    }
}