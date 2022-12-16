package com.example.savelifeapp.ui.account

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savelifeapp.data.model.HistoryDonors
import com.example.savelifeapp.databinding.ActivityHistoryDonorBinding
import com.example.savelifeapp.ui.request.viewpager.receivedRequest.ReceivedViewModel
import com.example.savelifeapp.utils.UiState
import com.example.savelifeapp.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryDonorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryDonorBinding
    private val viewModel: ReceivedViewModel by viewModels()
    private lateinit var list: ArrayList<HistoryDonors>
    lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDonorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = arrayListOf()
        binding.rvRiwayat.setHasFixedSize(true)
        binding.rvRiwayat.layoutManager = LinearLayoutManager(this)
        historyAdapter = HistoryAdapter(list)
        binding.rvRiwayat.adapter = historyAdapter
        viewModel.showMyHistory(list, historyAdapter)
        observer()
        supportActionBar?.hide()
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun observer() {
        viewModel.showHistory.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    toast("Loading dulu ya")
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
}