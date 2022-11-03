package com.example.savelifeapp.ui.request.viewpager.detailRequest

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.databinding.ActivityDetailMyRequestBinding
import com.example.savelifeapp.ui.request.viewpager.createRequest.CreateRequestViewModel
import com.example.savelifeapp.ui.request.viewpager.myRequest.MrequestAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMyRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMyRequestBinding

    private val viewModels: CreateRequestViewModel by viewModels()

    lateinit var requestPagerAdapater: MrequestAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMyRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

//        observer()
        val intent: Intent = intent
        var request: CreateRequest? = intent.getParcelableExtra("data_request")
        binding.btnDelete.setOnClickListener {
            if (request != null)
                viewModels.deleteRequest(request)
        }


//        binding.apply {
//            back.setOnClickListener {
//                onBackPressed()
//            }
//            idName.text = request?.name.toString()
//            idLocation.text = request?.lokasi.toString()
//            idDarah.text = request?.golDarah.toString()
//            idKeterangan.text = request?.keterangan.toString()
//            auth = FirebaseAuth.getInstance()
//            db = FirebaseFirestore.getInstance()
//            btnDelete.setOnClickListener {
//                db.collection("Request").document(auth.currentUser?.uid.toString())
//                    .collection("MyRequest").document(request?.id.toString())
//                    .delete()
//                    .addOnSuccessListener {
////                        requestPagerAdapater.notifyDataSetChanged()
//                        val intent = Intent(this@DetailMyRequestActivity, HomeActivity::class.java)
//                        startActivity(intent)
//                        Toast.makeText(
//                            this@DetailMyRequestActivity,
//                            "delete succes",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//            }
//        }
    }


//    private fun observer() {
//        viewModels.deleteRequest().observe(this) { state ->
//            when (state) {
//                is UiState.Loading -> {
//                    toast("Loading dulu ya")
//                }
//                is UiState.Failure -> {
//                    toast(state.error)
//                }
//                is UiState.Success -> {
//                    toast(state.data)
//                    val intent = Intent(this@DetailMyRequestActivity, HomeActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        }
//    }
}