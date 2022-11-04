package com.example.savelifeapp.ui.request.viewpager.detailRequest

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.savelifeapp.R
import com.example.savelifeapp.data.model.CreateRequest
import com.example.savelifeapp.databinding.ActivityUpdateRequestBinding
import com.example.savelifeapp.ui.HomeActivity
import com.example.savelifeapp.ui.request.viewpager.createRequest.CreateRequestViewModel
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UpdateRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateRequestBinding
    private val viewModels: CreateRequestViewModel by viewModels()
    private lateinit var createRequest: CreateRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = intent
        var request: CreateRequest? = intent.getParcelableExtra("update_request")

        supportActionBar?.hide()

        val bloodType = resources.getStringArray(R.array.blood_type)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, bloodType)
        binding.pilih.setAdapter(arrayAdapter)

        binding.nama.setText(request?.name)
        binding.keterangan.setText(request?.keterangan)
        binding.location.setText(request?.lokasi)


//        ini untuk mengambil positio dropdown darah teakhir
//        val position =
//            binding.pilih.setText(arrayAdapter.getPosition(request?.golDarah).toString(), false)
        val p = arrayAdapter.getPosition(request?.golDarah).toInt()
        binding.pilih.setText(arrayAdapter.getItem(p).toString(), false)
        
        observer()
        binding.btnUpdate.setOnClickListener {
            viewModels.updateRequest(getUpdateObj(), request?.id.toString())
        }
    }


    private fun getUpdateObj(): CreateRequest {
        createRequest = CreateRequest(
            name = binding.nama.text.toString(),
            lokasi = binding.location.text.toString(),
            golDarah = binding.pilih.text.toString(),
            keterangan = binding.keterangan.text.toString()
        )
        return createRequest
    }

    private fun observer() {
        viewModels.updateRequest.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    toast("Loading dulu ya")
                }
                is UiState.Failure -> {
                    toast(state.error)
                }
                is UiState.Success -> {
                    toast(state.data)
                    val intent = Intent(this@UpdateRequestActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}