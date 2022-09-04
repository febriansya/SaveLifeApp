package com.example.savelifeapp.ui.request.viewpager.mrequest.createRequest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.createPermintaan.CreateRequest
import com.example.savelifeapp.data.model.jenisDarah
import com.example.savelifeapp.databinding.ActivityCreateRequestBinding
import com.example.savelifeapp.databinding.FragmentRequestPagerBinding
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateRequestActivity() : AppCompatActivity(),
    CreateRequestAdapter.golDarahListener {

    private lateinit var binding: ActivityCreateRequestBinding
    lateinit var recyler: RecyclerView
    private lateinit var jenisGolDarah: String

    val viewModels: CreateRequestViewModel by viewModels()

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
                viewModels.createRequest(createRequest = getUserObj())
            }
        }
    }

    fun addData(): ArrayList<jenisDarah> {
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

    fun getUserObj(): CreateRequest {
        return CreateRequest(
            name = binding.edtNamePasien.text.toString(),
            golDarah = jenisGolDarah.toString(),
            lokasi = binding.edtLocationPasien.text.toString(),
            keterangan = binding.edtKeteranganPasien.text.toString(),
        )
    }

    //    ngambil data dari adapterPosition untuk recylerview option jenis darah
    override fun onValueUbah(value: Int) {
        val jenis = resources.getStringArray(R.array.jenis_darah)
        for (i in jenis.indices) {
            val stok = jenisDarah(
                jenis = jenis[value]
            )
            jenisGolDarah = jenis[value]
        }
    }


    fun observer() {
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
                        this@CreateRequestActivity, FragmentRequestPagerBinding::class.java
                    )
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    fun validation(): Boolean {
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
        }
        return isValid
    }
}
