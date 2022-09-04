package com.example.savelifeapp.data.repository

import android.content.Context
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.utils.FireStoreCollection
import com.example.savelifeapp.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
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
                if (it.isSuccessful && auth.currentUser?.isEmailVerified != false) {
                    result.invoke(UiState.Success("Login successfully"))
                } else {
                    result.invoke(UiState.Success("email  not verifed"))
                }
            }.addOnFailureListener {
                result.invoke(UiState.Failure("Authentication failed, please check email and password"))
            }
    }

    override fun updateUserInfo(
        user: UsersApp,
        result: (UiState<String>) -> Unit
    ) {
        val document = database.collection(FireStoreCollection.USER).document(user.uuid)
        document.set(user)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("user has been add")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun registerUser(
        email: String,
        password: String,
        user: UsersApp,
        result: (UiState<String>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    user.uuid = it.result.user?.uid ?: ""
                    updateUserInfo(user) { state ->
                        when (state) {
                            is UiState.Success -> {
                                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        result.invoke(UiState.Success("Verifikasi email sudah dikirim"))
                                    } else {
                                        result.invoke(UiState.Success("gagal kirim verifikasi"))
                                    }
                                }
                                result.invoke(UiState.Success("Register Successfully"))
                            }
                            is UiState.Failure -> {
                                result.invoke(UiState.Failure(state.error))
                            }
                            else -> {

                            }
                        }
                    }
                } else {
                    try {
                        throw it.exception ?: java.lang.Exception("Invalid authenthication")
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        result.invoke(UiState.Failure("Authentication failed, Password should be at least 6 characters"))
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        result.invoke(UiState.Failure("Authentication failed, Invalid email entered"))
                    } catch (e: FirebaseAuthUserCollisionException) {
                        result.invoke(UiState.Failure("Authentication failed, Email already registered."))
                    } catch (e: Exception) {
                        result.invoke(UiState.Failure(e.message))
                    }
                }
            }
            .addOnFailureListener {
                result.invoke(UiState.Failure(it.localizedMessage))
            }
    }

    override fun logout(result: (UiState<String>) -> Unit) {
        auth.signOut()
        result.invoke(UiState.Success("Logout Successfully"))
    }

    override fun forgotPassword(email: String, result: (UiState<String>) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.invoke(UiState.Success("Email has been sent"))
                } else {
                    result.invoke(UiState.Failure(task.exception?.message))
                }
            }.addOnFailureListener {
//                if emailnya belum terdaftar
                result.invoke(UiState.Failure("Authentication failed, check email"))
            }
    }
}