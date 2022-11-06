package com.example.savelifeapp.data.repository

import android.content.Context
import android.media.metrics.Event
import android.util.Log
import com.example.savelifeapp.data.model.CalonPendonor
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.data.model.UsersApp
import com.example.savelifeapp.ui.request.viewpager.detailRequest.CalonPendonorRequestAdapter
import com.example.savelifeapp.ui.request.viewpager.myRequest.CellClickListener
import com.example.savelifeapp.ui.request.viewpager.myRequest.MrequestAdapter
import com.example.savelifeapp.ui.request.viewpager.receivedRequest.ReceivedAdapter
import com.example.savelifeapp.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    val database: FirebaseFirestore,
    val auth: FirebaseAuth,
    private val context: Context
) : AccountRespository {
    private lateinit var image: String
    private lateinit var darahku: String
    private lateinit var dataRequest: CreateRequest

    init {
        val id = auth.currentUser?.uid ?: ""
        //get Photo
        val darahCurrent = Firebase.firestore.collection("UserApp").document(id)
        darahCurrent.get().addOnCompleteListener {
            if (it.isSuccessful) {
                image = it.result.getString("image").toString()
            }
            Log.d("image", image)
        }
    }

    override suspend fun createRequest(
        createRequest: CreateRequest,
        result: (UiState<String>) -> Unit
    ) {
        val id = auth.currentUser?.uid ?: ""
        val request = database.collection("Request").document(id)
        val subOutRequest = request.collection("MyRequest").document()
        createRequest.id = subOutRequest.id.toString()
        createRequest.idPengirim = id.toString()
        subOutRequest.set(createRequest).addOnCompleteListener {
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

    //    retrive subcollection firestore to reyclerview
    override suspend fun showMyRequest(
        arrayList: ArrayList<CreateRequest>,
        adapter: MrequestAdapter,
        result: (UiState<List<CreateRequest>>) -> Unit
    ) {
        val id = auth.currentUser?.uid ?: ""
        val myref = database.collection("Request").document(id)
        val subCollectionCreate = myref.collection("MyRequest")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        result.invoke(UiState.Failure(error.message.toString()))
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        arrayList.add(dc.document.toObject(CreateRequest::class.java))
                        result.invoke(UiState.Success(arrayList.filterNotNull()))
                    }
                    adapter.notifyDataSetChanged()
                }
            })
    }


    override suspend fun deleteRequest(
        request: CreateRequest,
        id: String,
        result: (UiState<String>) -> Unit
    ) {
        val document = database.collection("Request")
            .document(auth.currentUser?.uid.toString())
            .collection("MyRequest").document(id)
        document
            .delete()
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Request has been deleted")
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

    override suspend fun updateRequest(
        request: CreateRequest,
        id: String,
        result: (UiState<String>) -> Unit
    ) {

        val document = database.collection("Request").document(auth.currentUser?.uid.toString())
            .collection("MyRequest").document(id)
        document.update("name", request.name)
        document.update("golDarah", request.golDarah)
        document.update("lokasi", request.lokasi)
        document.update("keterangan", request.keterangan)
        document.update("whatsapp", request.whatsapp)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Request has been updated")
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

    override suspend fun acceptRequest(
        calonPendonor: CalonPendonor,
        idUserPeminta: String,
        idRequestPeminta: String,
        result: (UiState<String>) -> Unit
    ) {

        val currentUser = auth.currentUser?.uid.toString()
        /**
         * 1. curerntUser
         * 2. idRequest yang mau di accept
         * 3. buat collection calon pendonor berdasarkan id request dan masukan id currentUser
         */

        val idCurrent = Firebase.firestore.collection("UserApp").document(currentUser)
        database.collection("UserApp").whereEqualTo("uuid", currentUser)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(
                    value: QuerySnapshot?, error: FirebaseFirestoreException?
                ) {
                    if (error != null) {
                        result.invoke(UiState.Failure(error.message.toString()))
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        val users = (dc.document.toObject(CalonPendonor::class.java))
                        calonPendonor.nama = users.nama
                        calonPendonor.image = users.image
                        calonPendonor.address = users.address
                        calonPendonor.hone = users.hone
                        calonPendonor.idAccRequest = idRequestPeminta

                        val document = database.collection("Request").document(idUserPeminta)
                            .collection("MyRequest").document(idRequestPeminta)
                            .collection("CalonPendonor").document(currentUser)
                        document.set(calonPendonor).addOnCompleteListener {
                            result.invoke(
                                UiState.Success("Berhasil jadi calon Pendonor")
                            )
                        }.addOnFailureListener {
                            result.invoke(
                                UiState.Failure(
                                    it.localizedMessage
                                )
                            )
                        }
//                        arrayList.add(users)
                    }
                }

            })
    }

    override suspend fun calonPendonor(
        arrayList: ArrayList<CalonPendonor>,
        idRequest: String,
        adapter: CalonPendonorRequestAdapter,
        result: (UiState<List<CalonPendonor>>) -> Unit
    ) {

        val document = database.collection("Request").document(auth.currentUser?.uid.toString())
            .collection("MyRequest").document(idRequest)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
//                    tampilakan calon pendonor berdasarkan id MyRequset
                    val calonPendonor = database.collectionGroup("CalonPendonor")
                        .whereEqualTo("idAccRequest", idRequest)
                        .addSnapshotListener(object : EventListener<QuerySnapshot> {
                            override fun onEvent(
                                value: QuerySnapshot?,
                                error: FirebaseFirestoreException?
                            ) {
                                if (error != null) {
                                    result.invoke(UiState.Failure(error.message.toString()))
                                    return
                                }
                                for (dc: DocumentChange in value?.documentChanges!!) {
                                    arrayList.add(dc.document.toObject(CalonPendonor::class.java))
                                    result.invoke(UiState.Success(arrayList.filterNotNull()))
                                }
                                adapter.notifyDataSetChanged()
                            }
                        })
                }
            }
    }


    //    get request with filter ketika darah yang diminta sama dengan darah user maka tampilkan
//    yang ditampilkan adalah darah yang sama dengan user
    override suspend fun showHelpRequest(
        arrayList: ArrayList<Received>,
        adapter: ReceivedAdapter,
        result: (UiState<List<Received>>) -> Unit
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
                                    result.invoke(UiState.Failure(error.message.toString()))
                                    return
                                }
                                for (dc: DocumentChange in value?.documentChanges!!) {
                                    arrayList.add(dc.document.toObject(Received::class.java))
                                    result.invoke(UiState.Success(arrayList.filterNotNull()))
                                }
                                adapter.notifyDataSetChanged()
                            }

                        })
            }
        }
    }
}
