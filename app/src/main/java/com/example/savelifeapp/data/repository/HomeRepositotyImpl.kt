package com.example.savelifeapp.data.repository

import com.example.savelifeapp.data.model.Kegiatan
import com.example.savelifeapp.ui.home.viepager.kegiatan.KegiatanAdapater
import com.example.savelifeapp.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class HomeRepositotyImpl @Inject constructor(
    val auth: FirebaseAuth,
    val database: FirebaseFirestore,
) : HomeRepository {


    override suspend fun getKegiatan(
        arrayList: ArrayList<Kegiatan>,
        adapter: KegiatanAdapater,
        result: (UiState<List<Kegiatan>>) -> Unit
    ) {

        val myRef = database.collection("Kegiatan")
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        result.invoke(UiState.Failure(error.message.toString()))
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        arrayList.add(dc.document.toObject(Kegiatan::class.java))
                        result.invoke(UiState.Success(arrayList.filterNotNull()))
                    }
                    adapter.notifyDataSetChanged()
                }
            })
    }
}