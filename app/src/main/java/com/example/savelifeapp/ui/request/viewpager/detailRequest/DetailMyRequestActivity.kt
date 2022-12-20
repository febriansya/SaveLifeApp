package com.example.savelifeapp.ui.request.viewpager.detailRequest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savelifeapp.data.model.CalonPendonor
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.data.model.Received
import com.example.savelifeapp.databinding.ActivityDetailMyRequestBinding
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.ui.request.viewpager.createRequest.CreateRequestViewModel
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.URLEncoder

@AndroidEntryPoint
class DetailMyRequestActivity : AppCompatActivity(),CalonPendonorClickListener {

    private lateinit var binding: ActivityDetailMyRequestBinding
    private val viewModels: CreateRequestViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var list: ArrayList<CalonPendonor>

    lateinit var calonPendonorAdapater: CalonPendonorRequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMyRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        observer()
        observerCalonP()
        val intent: Intent = intent
        var request: CreateRequest? = intent.getParcelableExtra("data_request")

        binding.apply {
            idName.text = request?.name.toString()
            idDarah.text = request?.golDarah.toString()
            idLocation.text = request?.lokasi.toString()
            idKeterangan.text = request?.keterangan.toString()
        }

        binding.btnDelete.setOnClickListener {

            val builder = AlertDialog.Builder(this@DetailMyRequestActivity)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    lifecycleScope.launch {
                        if (request != null) {
                            viewModels.deleteRequest(request, request.id.toString())
                        }
                    }
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()

//            lifecycleScope.launch {
//                if (request != null) {
//                    viewModels.deleteRequest(request, request.id.toString())
//                }
//            }
        }
        binding.back.setOnClickListener {
            onBackPressed()
        }

        binding.btnEditRequest.setOnClickListener {
            val intent = Intent(this, UpdateRequestActivity::class.java)
            intent.putExtra("update_request", request)
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        list = arrayListOf()

        calonPendonorAdapater = CalonPendonorRequestAdapter(list,this)
        binding.recyclerView.adapter = calonPendonorAdapater

        viewModels.getCalonPendonor(list, request?.id.toString(), calonPendonorAdapater)
    }


    private fun observerCalonP() {
        viewModels.getCalonPendonor.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    Log.d("loading", "loading")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                }
            }
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


    override fun onItemClicked(calonPendonor: CalonPendonor) {
        try {
            val packageManager = getPackageManager()
            val i = Intent(Intent.ACTION_VIEW)
            val url =
                "https://api.whatsapp.com/send?phone=" + "+62${calonPendonor?.hone}+text=" + URLEncoder.encode(
                    "hello assalamualaikum"
                )
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i)
            } else {
                Toast.makeText(
                    this@DetailMyRequestActivity,
                    "Please install whatsapp",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } catch (e: Exception) {
            Toast.makeText(
                this@DetailMyRequestActivity,
                "" + e.stackTrace,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}