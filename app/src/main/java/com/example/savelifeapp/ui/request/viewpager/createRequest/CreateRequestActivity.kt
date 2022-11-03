package com.example.savelifeapp.ui.request.viewpager.createRequest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.data.model.jenisDarah
import com.example.savelifeapp.databinding.ActivityCreateRequestBinding
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRequestActivity : AppCompatActivity(),
    CreateRequestAdapter.golDarahListener {
    private lateinit var binding: ActivityCreateRequestBinding
    private lateinit var recyler: RecyclerView
    private lateinit var auth: FirebaseAuth
    private val viewModels: CreateRequestViewModel by viewModels()
    private lateinit var image: String
    private lateinit var goldarah: String
    private var tampungValue = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        recyler = binding.recylerOfJenis

        showRecylerList()
        observer()
        binding.btnLanjutkan.setOnClickListener {
            if (validation()) {
                viewModels.createRequest(getUserObj())
            }
        }
        getImageFromUrl()
    }

    private fun getImageFromUrl() {
        auth = FirebaseAuth.getInstance()
        val id = auth.currentUser?.uid
        val darahCurrent = Firebase.firestore.collection("UserApp").document(id.toString())
        darahCurrent.get().addOnCompleteListener {
            if (it.isSuccessful) {
                image = it.result.getString("image").toString()
            }
            Log.d("image", image)
        }
    }

    //    ngambil data dari adapterPosition untuk recylerview option jenis darah
    override fun onValueUbah(value: Int) {
        tampungValue = value
        val jenis = resources.getStringArray(R.array.jenis_darah)
        for (i in jenis.indices) {
            goldarah = jenis[value]
        }
        Log.d("goldarah", goldarah)
    }

//    jenis darah
    private fun addData(): ArrayList<jenisDarah> {
        val stoks = ArrayList<jenisDarah>()
        val jenis = resources.getStringArray(R.array.jenis_darah)
        for (i in jenis.indices) {
            val stok = jenisDarah(
                jenis = jenis[i]
            )
            stoks.add(stok)
        }
        return stoks
    }

    private fun showRecylerList() {
        recyler.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        val listCreateStok = CreateRequestAdapter(addData(), this)
        recyler.adapter = listCreateStok
    }


    private fun getUserObj(): CreateRequest {
        return CreateRequest(
            name = binding.edtNamePasien.text.toString(),
            golDarah = goldarah,
            lokasi = binding.edtLocationPasien.text.toString(),
            keterangan = binding.edtKeteranganPasien.text.toString(),
            image = image,
            whatsapp = binding.edtWa.text.toString(),
        )
    }

    private fun observer() {
        viewModels.createRequest.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    toast("Process Loading")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    val intent = Intent(
                        this@CreateRequestActivity, HomeActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        when {
            binding.edtNamePasien.text.isNullOrEmpty() && binding.edtKeteranganPasien.text.isNullOrEmpty() && binding.edtLocationPasien.text.isNullOrEmpty() -> {
                toast("Semua Field tidak boleh kosong")
                isValid = false
                return isValid
            }
            binding.edtNamePasien.text.isEmpty() -> {
                isValid = false
                toast("nama pasien tidak boleh kosong")
                return isValid
            }
            binding.edtLocationPasien.text.isEmpty() -> {
                isValid = false
                toast("Location tidak boleh kosong")
                return isValid
            }
            binding.edtWa.text.isEmpty() -> {
                isValid = false
                toast("Wa tidak boleh kosong")
                return isValid
            }
        }
        return isValid
    }
}
