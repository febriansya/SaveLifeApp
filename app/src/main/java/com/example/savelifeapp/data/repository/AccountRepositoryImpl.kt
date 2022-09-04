package com.example.savelifeapp.data.repository
import com.example.savelifeapp.data.model.createPermintaan.CreateRequest
import com.example.savelifeapp.utils.FireStoreCollection
import com.example.savelifeapp.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountRepositoryImpl(
    val database: FirebaseFirestore,
    val auth: FirebaseAuth,
) : AccountRespository {
    override fun createRequest(
        createRequest: CreateRequest,
        result: (UiState<String>) -> Unit
    ) {
        val id= auth.currentUser?.uid ?: ""
        val myref = database.collection(FireStoreCollection.USER).document(id)
        val subCollectionCreate = myref.collection("Request").document()
        subCollectionCreate.set(createRequest)
            .addOnCompleteListener {
                result.invoke(
                    UiState.Success("Request has been created")
                )
            }.addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    override fun acceptRequest() {
        TODO("Not yet implemented")
    }

}