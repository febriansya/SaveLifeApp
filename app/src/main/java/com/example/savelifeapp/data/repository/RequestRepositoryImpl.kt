package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.CalonPendonor
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class RequestRepositoryImpl @Inject constructor(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore,
) : RequestRepository {

    private lateinit var darahku: String

    override suspend fun CalonPendonorStatus(
        arrayReceived: ArrayList<Received>,
        arrayCalon: ArrayList<CalonPendonor>
    ) {
        val currentUser = auth.currentUser?.uid.toString()
        val darahCurrent = Firebase.firestore.collection("UserApp").document(currentUser)
        darahCurrent.get().addOnCompleteListener {
            if (it.isSuccessful) {
                darahku = it.result.getString("golDarah").toString()
//                menggunakan collectionn group karena mengakses subcollection
                val collect =
                    database.collectionGroup("MyRequest").whereEqualTo("golDarah", darahku)
                        .addSnapshotListener(object : EventListener<QuerySnapshot> {
                            override fun onEvent(
                                value: QuerySnapshot?, error: FirebaseFirestoreException?
                            ) {
                                if (error != null) {
                                    return
                                }
                                for (dc: DocumentChange in value?.documentChanges!!) {
                                    arrayReceived.add(dc.document.toObject(Received::class.java))
                                }
                            }
                        })
            }
        }


//        getCalonPedonor
        val document = database.collection("Request").document(auth.currentUser?.uid.toString())
            .collection("MyRequest").get()
            .addOnCompleteListener { task ->
                for (snapshot in task.result) {
                    database.collection("Request")
                        .document(auth.currentUser?.uid.toString())
                        .collection("MyRequest").document(snapshot.id).get()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val calonPendonor = database.collectionGroup("CalonPendonor")
                                    .whereEqualTo("id", snapshot.id)
                                    .addSnapshotListener(object : EventListener<QuerySnapshot> {
                                        override fun onEvent(
                                            value: QuerySnapshot?,
                                            error: FirebaseFirestoreException?
                                        ) {
                                            if (error != null) {
                                                return
                                            }
                                            for (dc: DocumentChange in value?.documentChanges!!) {
                                                arrayCalon.add(dc.document.toObject(CalonPendonor::class.java))
                                            }
                                        }
                                    })
                            }
                        }
                }
            }
    }
}