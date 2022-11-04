package com.example.savelifeapp.ui.request.viewpager.detailRequest

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.databinding.ActivityDetailMyRequestBinding
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.ui.request.viewpager.createRequest.CreateRequestViewModel
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailMyRequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailMyRequestBinding
    private val viewModels: CreateRequestViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMyRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        observer()

        val intent: Intent = intent
        var request: CreateRequest? = intent.getParcelableExtra("data_request")

        binding.apply {
            idName.text = request?.name.toString()
            idDarah.text = request?.golDarah.toString()
            idLocation.text = request?.lokasi.toString()
            idKeterangan.text = request?.keterangan.toString()
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                if (request != null) {
                    viewModels.deleteRequest(request, request.id.toString())
                }
            }
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnEditRequest.setOnClickListener {
            val intent = Intent(this, UpdateRequestActivity::class.java)
            intent.putExtra("update_request", request)
            startActivity(intent)
        }
    }


    private fun observer() {
        viewModels.delRequest.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    toast("Loading dulu ya")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    val intent = Intent(this@DetailMyRequestActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}